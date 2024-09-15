package vn.edu.likelion.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.edu.likelion.config.VNPayConfig;
import vn.edu.likelion.entity.*;
import vn.edu.likelion.exception.ApiException;
import vn.edu.likelion.exception.CustomHttpStatus;
import vn.edu.likelion.exception.ResourceNotFoundException;
import vn.edu.likelion.model.order.OrderRequest;
import vn.edu.likelion.model.order.OrderResponse;
import vn.edu.likelion.model.payment.PaymentDTO;
import vn.edu.likelion.repository.CourseRepository;
import vn.edu.likelion.repository.OrderRepository;
import vn.edu.likelion.repository.TrackCourseRepository;
import vn.edu.likelion.repository.UserRepository;
import vn.edu.likelion.service.IOrderService;
import vn.edu.likelion.utility.AppConstant;
import vn.edu.likelion.utility.VNPayUtility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {
    private final VNPayConfig vnPayConfig;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final TrackCourseRepository trackCourseRepository;

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

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        String email = AppConstant.getEmailFromContextHolder();

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));

        Course course = courseRepository.findById(orderRequest.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", orderRequest.getCourseId()));

        if(orderRepository.existsOrderByUserAndCourse(user, course))
            throw new ApiException(CustomHttpStatus.COURSE_PURCHASE);

        Order order = new Order(course.getNewPrice(), LocalDateTime.now(),
                user, course);

        Order savedOrder = orderRepository.save(order);

        List<TrackCourse> listTrackCourses = new ArrayList<>();
        TrackCourse trackCourse = null;
        for (Chapter chapter : course.getListChapters()){
            for (Lesson lesson : chapter.getListLessons()){
                trackCourse = new TrackCourse(user, lesson);
                listTrackCourses.add(trackCourse);
            }
        }

        if(!listTrackCourses.isEmpty()){
            listTrackCourses.get(0).setUnlock(true);
            listTrackCourses.get(0).setCurrent(true);
        }

        trackCourseRepository.saveAll(listTrackCourses);

        return convertEntityToResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getMyCourse() {
        String email = AppConstant.getEmailFromContextHolder();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));

        List<Order> listOrders = orderRepository.findAllByUser(user);
        if(listOrders.isEmpty()) throw new ApiException(CustomHttpStatus.LIST_COURSE_EMPTY);
        return listOrders.stream().map(this::convertEntityToResponse).toList();
    }

    private OrderResponse convertEntityToResponse(Order order){
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        orderResponse.setCustomerName(order.getUser().getFullName());
        orderResponse.setCourseTitle(order.getCourse().getTitle());
        orderResponse.setCourseThumbnail(order.getCourse().getThumbnail());
        orderResponse.setSlug(order.getCourse().getSlug());
        return orderResponse;
    }
}
