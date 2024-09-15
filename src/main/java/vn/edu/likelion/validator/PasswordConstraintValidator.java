package vn.edu.likelion.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        // Kiểm tra có ít nhất một ký tự in hoa
        if (!password.matches(".*[A-Z].*")) {
            buildViolationMessage(context, "Password phải chứa ít nhất một ký tự in hoa.");
            return false;
        }

        // Kiểm tra có ít nhất một ký tự số
        if (!password.matches(".*[0-9].*")) {
            buildViolationMessage(context, "Password phải chứa ít nhất một chữ số.");
            return false;
        }

        // Kiểm tra có ít nhất một ký tự đặc biệt
        if (!password.matches(".*[!@#$%^&*()-+=].*")) {
            buildViolationMessage(context, "Password phải chứa ít nhất một ký tự đặc biệt.");
            return false;
        }

        // Kiểm tra không chứa khoảng trắng
        if (password.contains(" ")) {
            buildViolationMessage(context, "Password không được chứa khoảng trắng.");
            return false;
        }
        return true;
    }

    private void buildViolationMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
