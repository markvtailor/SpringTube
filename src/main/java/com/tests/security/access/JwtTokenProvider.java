package com.tests.security.access;

import com.tests.security.Models.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {

    @Value("${auth.jwt.secret}")
    private String secretKey;

    @Getter
    @Value("${auth.jwt.auth}")
    private String authCookieName;

    @Getter
    @Value("${auth.jwt.refresh}")
    private String refreshCookieName;

    @Getter
    @Value("${auth.jwt.expiration-auth}")
    private int authExpirationCookie;


    @Getter
    @Value("${auth.jwt.path}")
    private String cookiePath;

    @PostConstruct
    public void init(){
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createTokenFromUsername(String username){
        Date now = new Date();
        Date valid = new Date(now.getTime() + getAuthExpirationCookie());
        return Jwts.builder().setSubject(username).
                setIssuedAt(now).setExpiration(valid).
                signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }
    public String createAuthToken(UserDetailsImpl userDetails){
            return createTokenFromUsername(userDetails.getUsername());

    }

    public boolean validateToken(String authToken){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return  true;
        }catch (ExpiredJwtException | SignatureException | MalformedJwtException | IllegalArgumentException | UnsupportedJwtException e){
            log.error(e.getLocalizedMessage());
        }
        return false;
    }
    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    }



