package vn.edu.likelion.service;

import com.nimbusds.jose.JOSEException;
import vn.edu.likelion.model.ApiResponse;
import vn.edu.likelion.model.user.*;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
    void logout(LogoutRequest request) throws ParseException, JOSEException;
    boolean introspect(String token) throws ParseException, JOSEException;
    AuthenticationResponse refreshToken(RefreshRequest refreshRequest) throws ParseException, JOSEException;
    ApiResponse resetPassword(String email);
    ApiResponse authToken(String token);
    ApiResponse savePassword(ChangePassword changePassword);
}
