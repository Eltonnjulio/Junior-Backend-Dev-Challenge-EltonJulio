package mz.co.app.api.configurations.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import mz.co.app.api.user.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class AuthTokenService {
    @Value("${app.jwt.expiration}")
    private String expiration;
    @Value("${app.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authenticate) {
        User user = (User) authenticate.getPrincipal();
        Date today = new Date();
        Date expireDate = new Date(today.getTime()+Long.parseLong(expiration));
        return Jwts.builder()
                .setIssuer("Blog API")
                .setSubject(user.getUsername())
                .setIssuedAt(today)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
