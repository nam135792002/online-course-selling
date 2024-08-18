package vn.edu.likelion.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import vn.edu.likelion.exception.CustomHttpStatus;
import vn.edu.likelion.exception.ErrorDetails;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(CustomHttpStatus.UNAUTHORIZED.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(CustomHttpStatus.UNAUTHORIZED.getCode());
        errorDetails.addError(CustomHttpStatus.UNAUTHORIZED.getMessage());
        errorDetails.setPath(request.getRequestURI());
        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
        response.flushBuffer();
    }
}
