package com.example.jobmatch.auth;

import io.jsonwebtoken.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

@Component
public class JwtUtility implements Serializable {
    @Resource
    private HttpServletRequest request;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiresIn}")
    private String expiresIn;

    public String generateToken(String userName) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(now)
                .setExpiration(new Date((new Date()).getTime() + Long.valueOf(expiresIn)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            request.setAttribute("errorJWT", "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            request.setAttribute("errorJWT", "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            request.setAttribute("errorJWT", "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            request.setAttribute("errorJWT", "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            request.setAttribute("errorJWT", "JWT claims string is empty.");
        }
        return false;
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public UserDetails userDetails(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails;
    }
}