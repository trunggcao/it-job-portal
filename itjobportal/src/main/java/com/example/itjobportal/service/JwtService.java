package com.example.itjobportal.service;

import com.example.itjobportal.dto.AuthDTO;
import com.example.itjobportal.entity.User;
import com.example.itjobportal.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtService {
    @Value("${jwt.secret}")
    private String secretkey;
    private final UserRepository userRepository;

    public String generateAccessToken(AuthDTO authDTO){
        User user = userRepository.findByEmail(authDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + authDTO.getEmail()));
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        Date issueTime = new Date();
        Date expiredTime = issueTime.from(issueTime.toInstant().plus(30, ChronoUnit.MINUTES));

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(authDTO.getEmail())
                .issueTime(issueTime)
                .expirationTime(expiredTime)
                .claim("roles", List.of(user.getRole().name()))
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);
        try {
            jwsObject.sign(new MACSigner(secretkey));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
        return jwsObject.serialize();
    }

    public boolean verifyToken(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        if (expirationTime.before(new Date())){
            return  false;
        }
        try {
            return  signedJWT.verify(new MACVerifier(secretkey));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
