package vn.edu.likelion.service;

import jakarta.servlet.http.HttpServletRequest;
import vn.edu.likelion.model.payment.PaymentDTO;

import java.io.UnsupportedEncodingException;

public interface OrderInterface {
    PaymentDTO createVnPayPayment(HttpServletRequest request) throws UnsupportedEncodingException;
}
