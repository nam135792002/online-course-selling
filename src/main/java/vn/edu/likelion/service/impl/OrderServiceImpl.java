package vn.edu.likelion.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.likelion.config.VNPayConfig;
import vn.edu.likelion.model.payment.PaymentDTO;
import vn.edu.likelion.service.OrderInterface;
import vn.edu.likelion.utility.VNPayUtility;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderInterface {
    private final VNPayConfig vnPayConfig;

    @Override
    public PaymentDTO createVnPayPayment(HttpServletRequest request) {
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtility.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtility.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtility.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtility.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return new PaymentDTO("ok","success", paymentUrl);
    }
}
