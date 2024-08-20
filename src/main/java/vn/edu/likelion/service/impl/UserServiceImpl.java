package vn.edu.likelion.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.likelion.entity.Role;
import vn.edu.likelion.entity.User;
import vn.edu.likelion.exception.ApiException;
import vn.edu.likelion.exception.CustomHttpStatus;
import vn.edu.likelion.exception.ResourceNotFoundException;
import vn.edu.likelion.model.user.UserInfoResponse;
import vn.edu.likelion.model.user.UserRegisterRequest;
import vn.edu.likelion.model.user.UserRegisterResponse;
import vn.edu.likelion.repository.UserRepository;
import vn.edu.likelion.service.UserInterface;
import vn.edu.likelion.utility.AppConstant;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Properties;

@Service
@Transactional
public class UserServiceImpl implements UserInterface {
    @Autowired private UserRepository userRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private PasswordEncoder passwordEncoder;

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
    public String verifyEmail(String email) {
        User user = userRepository.findUserByEmail(email);

        if(user == null) throw new ResourceNotFoundException("User", "Email", email);
        else{
            userRepository.enable(email);
            return "Xác nhận email thành công.";
        }
    }

    @Override
    public UserInfoResponse getMyInfo() {
        String email = AppConstant.getEmailFromContextHolder();

        User user = userRepository.findUserByEmail(email);
        if(user == null) throw new ApiException(CustomHttpStatus.EMAIL_NOT_EXISTED);

        return modelMapper.map(user, UserInfoResponse.class);
    }

    private void sendEmail(User user){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("tech.courses.895@gmail.com");
        mailSender.setPassword("giug qtcr yjag occm");
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


        content = content.replace("[[URL]]", "http://127.0.0.1:5500/Frontend/pages/Default/index.html");
        try {
            helper.setText(content,true);
        } catch (MessagingException e) {
            throw new ApiException(CustomHttpStatus.ERROR_SEND_EMAIL);
        }
        mailSender.send(message);
    }
}
