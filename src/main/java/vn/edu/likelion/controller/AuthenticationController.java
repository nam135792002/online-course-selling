package vn.edu.likelion.controller;

import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.model.user.AuthenticationRequest;
import vn.edu.likelion.model.user.LogoutRequest;
import vn.edu.likelion.model.user.RefreshRequest;
import vn.edu.likelion.service.impl.AuthenticationServiceImpl;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {
    @Autowired private AuthenticationServiceImpl authenticationService;

    @PostMapping("/token")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest logoutRequest) throws ParseException, JOSEException {
        authenticationService.logout(logoutRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody @Valid RefreshRequest refreshRequest) throws ParseException, JOSEException {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshRequest));
    }

}
