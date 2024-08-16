package vn.edu.likelion.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.model.user.UserRegisterRequest;
import vn.edu.likelion.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        return new ResponseEntity<>(userService.addUser(userRegisterRequest), HttpStatus.CREATED);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam(value = "code") String verificationCode,
                                    @RequestParam(value = "email") String email){
        return ResponseEntity.ok(userService.verifyEmail(email, verificationCode));
    }
}
