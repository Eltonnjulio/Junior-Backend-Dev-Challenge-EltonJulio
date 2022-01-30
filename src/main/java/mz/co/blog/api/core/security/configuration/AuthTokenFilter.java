package mz.co.blog.api.core.security.configuration;

import lombok.RequiredArgsConstructor;
import mz.co.blog.api.core.security.utils.AuthTokenService;
import mz.co.blog.api.user.domain.User;
import mz.co.blog.api.user.persistence.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
    private final AuthTokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = getToken(request);
        if (tokenService.isValid(token)) {
            try {
                User principal = userRepository.findByUsername(tokenService.getUsername(token)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (AuthenticationException fail) {
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null) {
            if (authorization.startsWith("Bearer ")) return authorization.substring(7);
        }
        return null;
    }
}