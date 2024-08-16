package vn.edu.likelion.model.user;

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
public class LoginDTO {

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Must be a well-formed email address")
    @Length(min = 5, max = 30, message = "Email have to 5-30 characters")
    private String email;

    @NotEmpty(message = "Password can not be empty")
    @Length(min = 8, max = 64, message = "Password have to 8-64 characters")
    private String password;
}
