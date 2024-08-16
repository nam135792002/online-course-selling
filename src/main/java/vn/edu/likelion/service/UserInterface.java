package vn.edu.likelion.service;

import vn.edu.likelion.model.user.UserRegisterRequest;
import vn.edu.likelion.model.user.UserRegisterResponse;

public interface UserInterface {
    UserRegisterResponse addUser(UserRegisterRequest userRegisterRequest);
    String verifyEmail(String email, String verificationCode);
}
