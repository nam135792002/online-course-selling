package vn.edu.likelion.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
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
import vn.edu.likelion.service.UserInterface;
import vn.edu.likelion.utility.AppConstant;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Properties;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserInterface {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final Cloudinary cloudinary;

    @Value("${spring.mail.password}")
    private String emailPassword;

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

        sendEmail(user);
        User savedUser = userRepository.save(user);
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

    private void sendEmail(User user){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("tech.courses.895@gmail.com");
        mailSender.setPassword(emailPassword);
        mailSender.setDefaultEncoding("utf-8");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        mailSender.setJavaMailProperties(properties);

        String toAddress = user.getEmail();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("tech.courses.895@gmail.com","Tech Courses");
            helper.setTo(toAddress);
            helper.setSubject(AppConstant.SUBJECT_REGISTER);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new ApiException(CustomHttpStatus.ERROR_SEND_EMAIL);
        }

        String content = AppConstant.CONTENT_REGISTER;


        content = content.replace("[[name]]",user.getFullName());


        content = content.replace("[[URL]]", "http://127.0.0.1:3000/Frontend/pages/BufferPage/BufferPage.html");
        try {
            helper.setText(content,true);
        } catch (MessagingException e) {
            throw new ApiException(CustomHttpStatus.ERROR_SEND_EMAIL);
        }
        mailSender.send(message);
    }
}
