package vn.edu.likelion.controller;

import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.exception.ErrorDetails;
import vn.edu.likelion.model.user.AuthenticationRequest;
import vn.edu.likelion.model.user.ChangePassword;
import vn.edu.likelion.model.user.LogoutRequest;
import vn.edu.likelion.model.user.RefreshRequest;
import vn.edu.likelion.service.impl.AuthenticationServiceImpl;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
@Tag(
        name = "Authentication Controller",
        description = "REST APIs related to Authentication, logout and refresh"
)
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer &  Account inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found user with email"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorDetails.class)
                    )
            )
    }
    )
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

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email){
        return ResponseEntity.ok(authenticationService.resetPassword(email));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> authPassword(@RequestParam String token){
        return ResponseEntity.ok(authenticationService.authToken(token));
    }

    @PutMapping("/save-password")
    public ResponseEntity<?> savePassword(@RequestBody @Valid ChangePassword changePassword){
        return ResponseEntity.ok(authenticationService.savePassword(changePassword));
    }
}
