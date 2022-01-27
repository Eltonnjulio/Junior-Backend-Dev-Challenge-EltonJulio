package mz.co.blog.api.core.security.configuration;

import lombok.RequiredArgsConstructor;
import mz.co.blog.api.core.security.service.AuthTokenService;
import mz.co.blog.api.user.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
  private final AuthTokenService tokenService;
  private final AuthenticationEntryPoint entryPoint;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    String apiKey = convert(request);
    if (apiKey == null) {
      chain.doFilter(request, response);
      return;
    } else {
      try {
        User principal = tokenService.getPrincipal(apiKey);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, apiKey, principal.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      } catch (AuthenticationException fail) {
        SecurityContextHolder.clearContext();
      }
    }
    chain.doFilter(request, response);
  }

  public static String convert(HttpServletRequest request) {
    String authorization = request.getHeader("Authorization");
    if (authorization != null) {
      if (authorization.startsWith("Bearer")) return authorization.substring(7);
      return authorization;
    }
    return null;
  }
}