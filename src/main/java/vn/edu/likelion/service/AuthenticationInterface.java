package vn.edu.likelion.service;

import com.nimbusds.jose.JOSEException;
import vn.edu.likelion.model.user.AuthenticationRequest;
import vn.edu.likelion.model.user.AuthenticationResponse;
import vn.edu.likelion.model.user.LogoutRequest;
import vn.edu.likelion.model.user.RefreshRequest;

import java.text.ParseException;

public interface AuthenticationInterface {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    void logout(LogoutRequest request) throws ParseException, JOSEException;
    boolean introspect(String token) throws ParseException, JOSEException;
    AuthenticationResponse refreshToken(RefreshRequest refreshRequest) throws ParseException, JOSEException;

}
