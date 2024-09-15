package vn.edu.likelion.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.edu.likelion.validator.ValidPassword;

@Setter
@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChangePassword {

    @NotEmpty(message = "Mật khẩu cũ không được để trống")
    @Size(min = 8, max = 30, message = "Mật khẩu có từ 8-30 ký tự")
    @JsonProperty("old_password")
    String oldPassword;

    @NotEmpty(message = "Mật khẩu mới không được để trống")
    @Size(min = 8, max = 30, message = "Mật khẩu có từ 8-30 ký tự")
    @JsonProperty("new_password")
    @ValidPassword
    String newPassword;

    String token;
}
