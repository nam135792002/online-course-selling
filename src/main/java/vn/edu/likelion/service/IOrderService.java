package vn.edu.likelion.service;

import jakarta.servlet.http.HttpServletRequest;
import vn.edu.likelion.model.order.OrderRequest;
import vn.edu.likelion.model.order.OrderResponse;
import vn.edu.likelion.model.payment.PaymentDTO;

import java.util.List;

public interface IOrderService {
    PaymentDTO createVnPayPayment(HttpServletRequest request);
    OrderResponse createOrder(OrderRequest orderRequest);
    List<OrderResponse> getMyCourse();
}
