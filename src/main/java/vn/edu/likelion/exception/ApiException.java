package vn.edu.likelion.exception;

import lombok.*;

@Setter
@Getter
public class ApiException extends RuntimeException{

    private final CustomHttpStatus customHttpStatus;

    public ApiException(CustomHttpStatus customHttpStatus) {
        super(customHttpStatus.getMessage());
        this.customHttpStatus = customHttpStatus;
    }
}
