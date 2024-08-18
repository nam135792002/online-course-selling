package vn.edu.likelion.exception;

import lombok.Data;
import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{

    private CustomHttpStatus customHttpStatus;

    public ApiException(CustomHttpStatus customHttpStatus) {
        super(customHttpStatus.getMessage());
        this.customHttpStatus = customHttpStatus;
    }
}
