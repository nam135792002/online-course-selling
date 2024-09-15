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
    LIST_COURSE_EMPTY(1011, "Danh sách khóa học của bạn đang rỗng",HttpStatus.NOT_FOUND),
    NOT_PURCHASE(1012, "Bạn chưa mua khóa học này",HttpStatus.BAD_REQUEST),
    NOT_LESSON(1014, "Bạn không được học bài giảng này",HttpStatus.BAD_REQUEST),
    NOT_ACCESS_LESSON(1015, "Bài học này chưa được mở khóa",HttpStatus.BAD_REQUEST),
    NOT_EXISTED_LESSON(1016, "Không tồn tại bài học trong khóa học này",HttpStatus.BAD_REQUEST),
    LOAD_IMAGE_FAILED(1017, "Upload ảnh thất bại",HttpStatus.BAD_REQUEST),
    USER_COMMENTED(1018, "Bạn đã đánh giá khóa học này",HttpStatus.CONFLICT),
    USER_NOT_COMMENT(1019, "Bạn không có quyền thay đổi review",HttpStatus.FORBIDDEN),
    REVIEW_IS_EMPTY(1020, "Chưa có review nào dành cho khóa học này",HttpStatus.NOT_FOUND),
    USER_IS_ACTIVE(1021, "Tài khoản đã được kích hoạt",HttpStatus.CONFLICT),
    LOGIN_FAILED(1022, "Đăng nhập thất bại",HttpStatus.UNAUTHORIZED),
    NOT_MATCH_PASSWORD(1023, "Mật khẩu cũ không trùng khớp",HttpStatus.CONFLICT),
    OLD_DUPLICATE_NEW_PASSWORD(1024, "Mật khẩu mới trùng với mật khẩu hiện tại",HttpStatus.CONFLICT),
    TOKEN_NOT_EXISTED(1025, "Mã xác thực không hợp lệ",HttpStatus.NOT_FOUND),
    TOKEN_EXPIRED(1026, "Mã xác thực đã hết hạn",HttpStatus.BAD_REQUEST),
    TOKEN_IS_ACTIVE(1027, "Mã đã được xác thực trước đó",HttpStatus.BAD_REQUEST);





    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    CustomHttpStatus(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
