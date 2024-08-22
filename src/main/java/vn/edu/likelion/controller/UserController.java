package vn.edu.likelion.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.likelion.model.user.UserRegisterRequest;
import vn.edu.likelion.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        return new ResponseEntity<>(userService.addUser(userRegisterRequest), HttpStatus.CREATED);
    }

    @PutMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam(value = "email") String email){
        return ResponseEntity.ok(userService.verifyEmail(email));
    }

    @GetMapping("/my-info")
    public ResponseEntity<?> myInfo(){
        return ResponseEntity.ok(userService.getMyInfo());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(){
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestParam(value = "full_name", required = false) String fullName,
                                           @RequestParam(value = "image", required = false) MultipartFile multipartFile){
        return ResponseEntity.ok(userService.updateUser(fullName, multipartFile));
    }
}
