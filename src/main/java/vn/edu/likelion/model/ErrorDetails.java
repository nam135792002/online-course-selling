package vn.edu.likelion.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErrorDetails {
    private LocalDateTime timestamp;
    private int status;
    private String path;
    private List<String> errors = new ArrayList<>();

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addError(String e){
        this.errors.add(e);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public List<String> getErrors() {
        return errors;
    }
}
