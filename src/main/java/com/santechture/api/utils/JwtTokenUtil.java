package com.santechture.api.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * @author rehab.abd-elhamid
 */
//http://www.devglan.com/spring-security/spring-boot-jwt-auth
@Component
public class JwtTokenUtil implements Serializable {


    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(String userName) {
        //subject-->username authenticated
        //claims-->
        //issuedat-->authentication time
        //signWith-->
        //SIGNING_KEY-->must be complex and on externalresourse outsidesource code to be secure

        String jwtToken = "";
        jwtToken = Jwts.builder().setSubject(userName).claim("roles", "ADMIN")
                .setExpiration(new Date(System.currentTimeMillis() + ConstantStrings.TOKEN_LIFETIME))

                .signWith(SignatureAlgorithm.HS256,ConstantStrings.SIGNING_KEY).compact();
        return jwtToken;
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }




    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser()
                .setSigningKey(ConstantStrings.SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    //use UserDetails
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username.equals(userDetails.getUsername())
                        && !isTokenExpired(token));
    }

}