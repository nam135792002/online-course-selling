package vn.edu.likelion.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // handle specific exception
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorDetails handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                        WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setPath(webRequest.getDescription(false));
        errorDetails.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetails.addError(exception.getMessage());
        return errorDetails;
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleAPIException(ApiException exception,
                                                               WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails();
        CustomHttpStatus customHttpStatus = exception.getCustomHttpStatus();
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setPath(webRequest.getDescription(false));
        errorDetails.setStatus(customHttpStatus.getCode());
        errorDetails.addError(customHttpStatus.getMessage());
        return new ResponseEntity<>(errorDetails, customHttpStatus.getHttpStatusCode());
    }

    // global exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDetails handleGlobalException(Exception exception,
                                                               WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setPath(webRequest.getDescription(false));
        errorDetails.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.addError(exception.getMessage());
        return errorDetails;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetails.setPath(((ServletWebRequest) request).getRequest().getServletPath());
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> errorDetails.addError(fieldError.getDefaultMessage()));
        return new ResponseEntity<>(errorDetails, headers, status);
    }

}
