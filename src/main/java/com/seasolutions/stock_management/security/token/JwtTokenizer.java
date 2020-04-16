package com.seasolutions.stock_management.security.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seasolutions.stock_management.model.exception.AuthenticationException;
import com.seasolutions.stock_management.model.view_model.EmployeeViewModel;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Date;



@Component
public class JwtTokenizer implements Tokenizer {

    @Value("${security.session.timeToLiveInSeconds}")
    private long sessionTimeToLiveInSeconds =0;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String CLAIMS_DATA = "data";
    private static final SecretKey SECRET_KEY = new SecretKeySpec(
            "494847a9c8a147bf82f4ca6da59efe61".getBytes(),
            SignatureAlgorithm.HS512.getJcaName()
    );



    @Override
    public String getToken(EmployeeViewModel employee) {
        final Date expirationDate = new Date(System.currentTimeMillis() + (sessionTimeToLiveInSeconds * 1000));
        final TokenPayload payload = new TokenPayload(employee);
        try {
            return Jwts.builder()
                    .setSubject(String.valueOf(employee.getId()))
                    .setExpiration(expirationDate)
                    .claim(CLAIMS_DATA, OBJECT_MAPPER.writeValueAsString(payload))
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();
        } catch (final JsonProcessingException e) {
            throw new RuntimeException("Failed to create token", e);
        }
    }



    @Override
    public TokenPayload verifyTokenAndGetPayload(String token) {
        try {
            final Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            final String tokenPayloadString = claimsJws.getBody().get(CLAIMS_DATA).toString();
            return OBJECT_MAPPER
                    .readValue(tokenPayloadString, TokenPayload.class);
        } catch (final JwtException | IOException e) {
            throw new AuthenticationException("Failed to verify token", e);
        }
    }
}
