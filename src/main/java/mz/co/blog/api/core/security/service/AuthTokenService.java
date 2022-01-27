package mz.co.blog.api.core.security.service;

import lombok.RequiredArgsConstructor;
import mz.co.blog.api.core.security.model.AuthToken;
import mz.co.blog.api.core.security.persistence.AuthTokenRepository;
import mz.co.blog.api.core.security.presentation.AuthTokenDto;
import mz.co.blog.api.user.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AuthTokenService implements ApplicationContextAware {
  public static final RuntimeException EX_INVALID_TOKEN = new UsernameNotFoundException("Invalid Token.");
  public static final RuntimeException EX_EXPIRED_TOKEN = new UsernameNotFoundException("The supplied Token has expired.");
  public static final RuntimeException EX_TOKEN_BLACKLISTED = new UsernameNotFoundException("The supplied Token has been blacklisted.");

  private final AuthTokenRepository tokenRepository;
  private ApplicationContext context;

  public AuthTokenDto createToken(User user) {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime validUntil = now.plusHours(3);
    LocalDateTime refreshUntil = now.plusHours(2);
    String token = getPasswordEncoder().encode(now.toString());
    return new AuthTokenDto(tokenRepository.save(new AuthToken(token, validUntil, refreshUntil, user)));
  }

  public AuthTokenDto createToken(String username, String password) {
    Authentication auth = getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
    return createToken((User) auth.getPrincipal());
  }

  public User getPrincipal(String token) {
    AuthToken authToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> EX_INVALID_TOKEN);
    validateToken(authToken);
    return authToken.getUser();
  }

  private AuthenticationManager getAuthenticationManager() {
    return context.getBean(AuthenticationManager.class);
  }

  private PasswordEncoder getPasswordEncoder() {
    return context.getBean(PasswordEncoder.class);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

  public AuthTokenDto refresh(String token) {
    AuthToken authToken = tokenRepository.findByToken(token).orElseThrow(() -> EX_INVALID_TOKEN);
    try {
      validateToken(authToken);
    } catch (AuthenticationException e) {
      if (!e.equals(EX_EXPIRED_TOKEN)) throw e;
    }

    if (authToken.getRefreshUntil().isBefore(LocalDateTime.now())) throw EX_EXPIRED_TOKEN;
    invalidate(authToken);
    return createToken(authToken.getUser());
  }

  private void validateToken(AuthToken token) {
    if (token.isBlacklisted()) throw EX_TOKEN_BLACKLISTED;
    if (token.getValidUntil().isBefore(LocalDateTime.now())) throw EX_EXPIRED_TOKEN;
  }

  public void invalidate(String token) {
    AuthToken authToken = tokenRepository.findByToken(token).orElseThrow(() -> EX_INVALID_TOKEN);
    validateToken(authToken);
    invalidate(authToken);
  }

  private void invalidate(AuthToken token) {
    tokenRepository.save(token.setBlacklisted(true));
  }

  @Scheduled(cron = "0 0 0 1 * *")
  @Transactional
  public void cleanup() {
    tokenRepository.deleteExpiredTokens();
  }
}
