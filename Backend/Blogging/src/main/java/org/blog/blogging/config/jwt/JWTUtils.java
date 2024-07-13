package org.blog.blogging.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JWTUtils {

    @Value("${security.jwt.secret-key}")
    private String JWTSECRET;

    @Value("${security.jwt.refresh-token.expiration}")
    private int JWTEXPIRATIONMS;

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.debug("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String generateTokenFromUsername(UserDetails userDetails) {
        String userName = userDetails.getUsername();
        return Jwts.builder()
                .subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + JWTEXPIRATIONMS))
                .signWith(key())
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWTSECRET));
    }

    public boolean validateJwtToken(String authToken)
    {
        try{

            Jwts.parser().verifyWith((SecretKey) key())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        }catch (MalformedJwtException e)
        {
            log.error("Invalid Jwt token: {}",e.getMessage());
        }
        catch (ExpiredJwtException e)
        {
            log.error("Jwt token expired: {}",e.getMessage());
        }
        catch (UnsupportedJwtException e)
        {
            log.error("Jwt token is UnSupported: {}",e.getMessage());
        }
        return false;
    }
}
