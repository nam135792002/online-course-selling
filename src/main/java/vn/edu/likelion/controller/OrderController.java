package vn.edu.likelion.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.model.order.OrderRequest;
import vn.edu.likelion.service.impl.OrderServiceImpl;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {
    @Autowired private OrderServiceImpl orderService;

    @PostMapping("/create")
    public ResponseEntity<?> add(@RequestBody OrderRequest orderRequest){
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }

    @GetMapping("/vn-pay")
    public ResponseEntity<?> pay(HttpServletRequest request){
        return ResponseEntity.ok(orderService.createVnPayPayment(request));
    }

    @GetMapping("/my-course")
    public ResponseEntity<?> getAllMyCourse(){
        return ResponseEntity.ok(orderService.getMyCourse());
    }
}
