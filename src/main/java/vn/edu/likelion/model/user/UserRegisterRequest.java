package vn.edu.likelion.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotEmpty(message = "Họ và tên không được để trống")
    @Length(min = 5, max = 45, message = "Họ và tên phải có 5-45 ký tự")
    @JsonProperty("full_name")
    private String fullName;

    @NotEmpty(message = "Email không được để trống")
    @Email(message = "Không đúng định dạng email")
    @Length(min = 5, max = 30, message = "Email phải có 5-30 ký tự")
    private String email;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @Length(min = 8, max = 64, message = "Mật khẩu phải có 8-64 ký tự")
    private String password;
}
