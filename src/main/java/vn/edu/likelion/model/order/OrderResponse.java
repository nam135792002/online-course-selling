package vn.edu.likelion.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Integer id;

    @JsonProperty("customer_name")
    String customerName;

    @JsonProperty("course_title")
    String courseTitle;

    @JsonProperty("course_thumbnail")
    String courseThumbnail;

    String slug;

    @JsonProperty("date_purchase")
    LocalDateTime createdPurchase;
}
