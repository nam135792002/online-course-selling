package vn.edu.likelion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @PostMapping("/buy")
    public ResponseEntity<?> order(){
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
}
