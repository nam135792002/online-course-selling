package vn.edu.likelion.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterResponse {
    private Integer id;

    @JsonProperty("full_name")
    private String fullName;

    private String email;

    @JsonProperty("created_time")
    private LocalDate createdTime;
}
