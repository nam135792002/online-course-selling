package vn.edu.likelion.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import vn.edu.likelion.exception.CustomHttpStatus;
import vn.edu.likelion.exception.ErrorDetails;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(CustomHttpStatus.UNAUTHENTICATED.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setStatus(CustomHttpStatus.UNAUTHENTICATED.getCode());
        errorDetails.addError(CustomHttpStatus.UNAUTHENTICATED.getMessage());
        errorDetails.setPath(request.getRequestURI());
        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
        response.flushBuffer();
    }
}
