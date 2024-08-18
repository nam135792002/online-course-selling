package vn.edu.likelion.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum CustomHttpStatus {
    EMAIL_EXISTED(1001, "Email đã được đăng ký trước đó", HttpStatus.CONFLICT),
    ERROR_SEND_EMAIL(1002, "Lỗi gửi email xác nhận", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EXISTED(1003, "Email không tồn tại", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1004, "Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1005, "Bạn không có quyền truy cập",HttpStatus.FORBIDDEN),
    TOKEN_INVALID(1006, "Sai token",HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN(1007, "Đã hết hạn phiên đăng nhập", HttpStatus.UNAUTHORIZED);



    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    CustomHttpStatus(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
