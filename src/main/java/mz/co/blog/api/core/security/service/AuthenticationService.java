package mz.co.blog.api.core.security.service;

import lombok.RequiredArgsConstructor;
import mz.co.blog.api.core.security.model.AuthLoginRequest;
import mz.co.blog.api.core.security.presentation.AuthTokenDto;
import mz.co.blog.api.core.security.utils.AuthTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private  final AuthTokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthTokenDto createToken(AuthLoginRequest request){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
        try {
            Authentication authenticate = authenticationManager.authenticate(authentication);
            return new AuthTokenDto(tokenService.generateToken(authenticate),"Bearer");
        }catch (AuthenticationException exception){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
