package com.inn.cafe.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    //JSOn web token generated based on secret key
    private String secret= "cafE47@ziP";

    //extracts the subject(username) from the claims in the token
    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    //when token becomes invalid
    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    //Claims is from ioJwt
    //This is a generic method that can extract various claims (such as subject,
    // expiration, or custom claims) from the JWT
    public <T> T extractClaims (String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //extracts all claims from the tokens and we use ^
    //if someone tampers with the Jwt, token is automatically changed

    public Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //method to check if token is expired with the method extractexp
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims,username);
    }
    private String createToken(Map<String,Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //exoired in 10 hours
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        //checking is username is valid
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
}
