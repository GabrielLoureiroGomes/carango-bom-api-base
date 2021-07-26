package br.com.caelum.carangobom.service;

import br.com.caelum.carangobom.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Service
public class TokenService {

    @Value("${carangobom.jwt.expiration}")
    private String expirationTime;

    @Value("${carangobom.jwt.secret}")
    private String secret;

    public String createToken(Authentication authentication) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        User user = (User) authentication.getPrincipal();
        Date now = new Date();
        Date exp = new Date(now.getTime() + Long.parseLong(expirationTime));

        return Jwts.builder()
                .setIssuer("TW Acelera - Group 1 ")
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        Claims body = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token).getBody();
        return Long.parseLong(body.getSubject());
    }
}
