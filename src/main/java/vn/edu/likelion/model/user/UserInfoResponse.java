package vn.edu.likelion.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {
    private Integer id;

    @JsonProperty("full_name")
    private String fullName;

    private String email;

    private String avatar;

    @JsonProperty("date_participate")
    private LocalDate createdTime;
}
