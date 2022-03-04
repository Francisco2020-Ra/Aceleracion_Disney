package com.alkemy.disney.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils{

        @Value("${alkemy.app.disney.SECRET_KEY}")
        private String SECRET_KEY;
        @Value("${alkemy.app.disney.JWT_EXPIRTAION_MS}")
        private int JWT_EXPIRTAION_MS;

        public String extractUsername(String token){ return extractClaim(token, Claims::getSubject);}

        public Date extractExpiration(String token){ return extractClaim(token, Claims::getExpiration);}

        public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }

        private Claims extractAllClaims(String token){
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        }

        private Boolean isTokenExpired(String token){return extractExpiration(token).before(new Date());}

        /** proporciona métodos para generar, analizar y validar JWT */
        public String generateToken(UserDetails userDetails){
            Map<String, Object> claims = new HashMap<>();
            return createToken(claims, userDetails.getUsername());
        }


        private String createToken(Map<String, Object> claims, String subject){
            return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + JWT_EXPIRTAION_MS))
                        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                        .compact();
        }

        public Boolean validateToken(String token, UserDetails userDetails){
            final String username = extractUsername(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }
}
