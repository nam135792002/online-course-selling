package vn.edu.likelion.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.likelion.entity.Role;
import vn.edu.likelion.entity.User;
import vn.edu.likelion.exception.ApiException;
import vn.edu.likelion.exception.CustomHttpStatus;
import vn.edu.likelion.exception.ResourceNotFoundException;
import vn.edu.likelion.model.ApiResponse;
import vn.edu.likelion.model.user.UserInfoResponse;
import vn.edu.likelion.model.user.UserRegisterRequest;
import vn.edu.likelion.model.user.UserRegisterResponse;
import vn.edu.likelion.repository.UserRepository;
import vn.edu.likelion.service.IUserService;
import vn.edu.likelion.utility.AppConstant;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final Cloudinary cloudinary;

    @Override
    public UserRegisterResponse addUser(UserRegisterRequest userRegisterRequest) {
        User user = modelMapper.map(userRegisterRequest, User.class);
        if(userRepository.existsUserByEmail(user.getEmail())){
            throw new ApiException(CustomHttpStatus.EMAIL_EXISTED);
        }
        String passEncode = passwordEncoder.encode(userRegisterRequest.getPassword());

        user.setAvatar("https://res.cloudinary.com/dqnoopa0x/image/upload/v1723775424/lsorq9sodc5qiey5nuhx.png");
        user.setPassword(passEncode);
        user.setCreatedTime(LocalDate.now());
        user.setRole(new Role(2));
        user.setEnabled(false);

        String urlAuth = "http://127.0.0.1:3000/Frontend/pages/BufferPage/BufferPage.html";
        User savedUser = userRepository.save(user);
        AppConstant.sendEmail(savedUser, urlAuth, AppConstant.SUBJECT_REGISTER, AppConstant.CONTENT_REGISTER);
        return modelMapper.map(savedUser, UserRegisterResponse.class);
    }

    @Override
    public ApiResponse verifyEmail(String email) {
        if(!userRepository.existsUserByEmail(email)) throw new ResourceNotFoundException("User", "email", email);
        if(userRepository.existsUserByEmailAndEnabled(email, true)) throw new ApiException(CustomHttpStatus.USER_IS_ACTIVE);

        userRepository.enable(email);
        return new ApiResponse("Xác nhận email thành công.");
    }

    @Override
    public UserInfoResponse getMyInfo() {
        String email = AppConstant.getEmailFromContextHolder();

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Email", email));

        return modelMapper.map(user, UserInfoResponse.class);
    }

    @Override
    public UserInfoResponse updateUser(String fullName, MultipartFile thumbnail) {
        String email = AppConstant.getEmailFromContextHolder();

        User userInDB = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Email", email));
        if(fullName != null){
            userInDB.setFullName(fullName);
        }

        if(thumbnail != null){
            deleteImageInCloudinary(userInDB.getAvatar());
            Map r;
            try {
                r = cloudinary.uploader().upload(thumbnail.getBytes(),
                        ObjectUtils.asMap("resource_type","auto"));
            } catch (IOException e) {
                throw new ApiException(CustomHttpStatus.LOAD_IMAGE_FAILED);
            }
            String img = (String) r.get("secure_url");
            userInDB.setAvatar(img);
        }
        User savedUser = userRepository.save(userInDB);
        return modelMapper.map(savedUser, UserInfoResponse.class);
    }

    public void deleteImageInCloudinary(String url){
        try {
            int lastSlashIndex = url.lastIndexOf('/');
            int lastDotIndex = url.lastIndexOf('.');
            String fileName = url.substring(lastSlashIndex + 1, lastDotIndex);
            if(!fileName.equals("lsorq9sodc5qiey5nuhx")){
                cloudinary.uploader().destroy(fileName, ObjectUtils.asMap("resource_type","image"));
            }
        } catch (IOException e) {
            throw new ApiException(CustomHttpStatus.LOAD_IMAGE_FAILED);
        }
    }

    @Override
    public ApiResponse changePassword(String oldPassword, String newPassword) {
        String email = AppConstant.getEmailFromContextHolder();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));

        boolean checkOldPass = passwordEncoder.matches(oldPassword, user.getPassword());
        boolean compareOldToNewPassword = passwordEncoder.matches(newPassword, user.getPassword());
        if(!checkOldPass){
            throw new ApiException(CustomHttpStatus.NOT_MATCH_PASSWORD);
        }
        if(compareOldToNewPassword){
            throw new ApiException(CustomHttpStatus.OLD_DUPLICATE_NEW_PASSWORD);
        }

        String passwordEncode = passwordEncoder.encode(newPassword);
        user.setPassword(passwordEncode);
        userRepository.save(user);
        return new ApiResponse("Thay đổi mật khẩu thành công");
    }
}
