package vn.edu.likelion.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.likelion.model.payment.PaymentDTO;
import vn.edu.likelion.service.impl.OrderServiceImpl;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {
    @Autowired private OrderServiceImpl orderService;

    @PostMapping("/buy")
    public ResponseEntity<?> order(){
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    @GetMapping("/vn-pay")
    public ResponseEntity<?> pay(HttpServletRequest request) throws UnsupportedEncodingException {
        return ResponseEntity.ok(orderService.createVnPayPayment(request));
    }

    @GetMapping("/vn-pay-callback")
    public ResponseEntity<?> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {

            return ResponseEntity.ok(new PaymentDTO("00", "Success", ""));
        } else {
            return new ResponseEntity<>(new PaymentDTO("1009", "Failed", ""), HttpStatus.BAD_REQUEST);
        }
    }
}
