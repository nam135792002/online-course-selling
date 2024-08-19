package vn.edu.likelion.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.likelion.entity.InvalidatedToken;
import vn.edu.likelion.entity.User;
import vn.edu.likelion.exception.ApiException;
import vn.edu.likelion.exception.CustomHttpStatus;
import vn.edu.likelion.model.user.AuthenticationRequest;
import vn.edu.likelion.model.user.AuthenticationResponse;
import vn.edu.likelion.model.user.LogoutRequest;
import vn.edu.likelion.model.user.RefreshRequest;
import vn.edu.likelion.repository.InvalidatedTokenRepository;
import vn.edu.likelion.repository.UserRepository;
import vn.edu.likelion.service.AuthenticationInterface;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationInterface {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private InvalidatedTokenRepository invalidatedTokenRepository;

    @Value("${jwt.signerKey}")
    private String signerKey;

    @Value("${jwt.valid-duration}")
    private long validDuration;

    @Value("${jwt.refreshable-duration}")
    private long refreshableDuration;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findUserByEmail(authenticationRequest.getEmail());
        if(user == null) throw new ApiException(CustomHttpStatus.EMAIL_NOT_EXISTED);
        if(!user.isEnabled()) throw new ApiException(CustomHttpStatus.USER_NOT_ACTIVE);
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(),
                user.getPassword());

        if(!authenticated) throw new ApiException((CustomHttpStatus.PASSWORD_INVALID));
        var token = generateToken(user);
        return new AuthenticationResponse(token);
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken = verifyJwt(request.getToken(), true);
        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = new InvalidatedToken(jit, expiryTime);
        invalidatedTokenRepository.save(invalidatedToken);
    }

    @Override
    public boolean introspect(String token) throws ParseException, JOSEException {
        boolean isValid = true;

        try {
            verifyJwt(token, false);
        } catch (ApiException e) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest refreshRequest) throws ParseException, JOSEException {
        var signJWT = verifyJwt(refreshRequest.getToken(), true);
        var jit = signJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = new InvalidatedToken(jit, expiryTime);
        invalidatedTokenRepository.save(invalidatedToken);

        var username = signJWT.getJWTClaimsSet().getSubject();
        var user = userRepository.findUserByEmail(username);
        if(user == null) throw new ApiException(CustomHttpStatus.UNAUTHENTICATED);
        var token = generateToken(user);
        return new AuthenticationResponse(token);
    }

    private SignedJWT verifyJwt(String token, boolean refresh) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(signerKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (refresh) ?
                new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                        .toInstant().plus(refreshableDuration, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(jwsVerifier);
        if(!(verified && expiryTime.after(new Date())))
            throw new ApiException(CustomHttpStatus.UNAUTHENTICATED);

        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new ApiException(CustomHttpStatus.UNAUTHENTICATED);
        }

        return signedJWT;
    }

    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issuer("likelion.edu.vn")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(validDuration, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(user.getRole() != null){
            stringJoiner.add(user.getRole().getName());
        }
        return stringJoiner.toString();
    }
}
