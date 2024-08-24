package vn.edu.likelion.model.trackcourse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackCourseRequest {

    @JsonProperty("current_time")
    LocalTime currentTime;
}
