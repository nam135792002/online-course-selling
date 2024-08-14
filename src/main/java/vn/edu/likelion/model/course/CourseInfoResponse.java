package vn.edu.likelion.model.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseInfoResponse {
    private Integer id;
    private String value;
    private String type;
}
