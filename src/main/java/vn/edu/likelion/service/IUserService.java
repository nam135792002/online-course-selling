package vn.edu.likelion.service;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.likelion.model.ApiResponse;
import vn.edu.likelion.model.user.UserInfoResponse;
import vn.edu.likelion.model.user.UserRegisterRequest;
import vn.edu.likelion.model.user.UserRegisterResponse;

public interface IUserService {
    UserRegisterResponse addUser(UserRegisterRequest userRegisterRequest);
    ApiResponse verifyEmail(String email);
    UserInfoResponse getMyInfo();
    UserInfoResponse updateUser(String fullName, MultipartFile thumbnail);
    ApiResponse changePassword(String oldPassword, String newPassword);
}
