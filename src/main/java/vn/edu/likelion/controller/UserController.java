package vn.edu.likelion.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.model.user.UserRegisterRequest;
import vn.edu.likelion.service.UserInterface;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserInterface userInterface;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        return new ResponseEntity<>(userInterface.addUser(userRegisterRequest), HttpStatus.CREATED);
    }

    @PutMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam(value = "email") String email){
        return ResponseEntity.ok(userInterface.verifyEmail(email));
    }

    @GetMapping("/my-info")
    public ResponseEntity<?> myInfo(){
        return ResponseEntity.ok(userInterface.getMyInfo());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(){
        return ResponseEntity.ok("Success");
    }
}
