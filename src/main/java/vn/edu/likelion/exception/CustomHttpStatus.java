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
    EXPIRED_TOKEN(1007, "Đã hết hạn phiên đăng nhập", HttpStatus.UNAUTHORIZED),
    USER_NOT_ACTIVE(1008, "Email chưa được xác thực", HttpStatus.UNAUTHORIZED),
    PASSWORD_INVALID(1009, "Sai mật khẩu",HttpStatus.UNAUTHORIZED),
    COURSE_PURCHASE(1010, "Bạn đã mua khóa học này trước đó",HttpStatus.CONFLICT),
    LIST_COURSE_EMPTY(1011, "Danh sách khóa học của bạn đang rỗng",HttpStatus.NO_CONTENT),
    NOT_PURCHASE(1012, "Bạn chưa mua khóa học này",HttpStatus.BAD_REQUEST),
    NOT_LESSON(1014, "Bạn không được học bài giảng này",HttpStatus.BAD_REQUEST),
    NOT_ACCESS_LESSON(1015, "Bài học này chưa được mở khóa",HttpStatus.BAD_REQUEST),
    NOT_EXISTED_LESSON(1016, "Không tồn tại bài học trong khóa học này",HttpStatus.BAD_REQUEST),
    LOAD_IMAGE_FAILED(1017, "Upload ảnh thất bại",HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    CustomHttpStatus(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
