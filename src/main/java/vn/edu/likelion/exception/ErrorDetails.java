package vn.edu.likelion.exception;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NonNull
public class ErrorDetails {
    private LocalDateTime timestamp;
    private int status;
    private String path;
    private List<String> errors = new ArrayList<>();

    public void addError(String e){
        this.errors.add(e);
    }
}
