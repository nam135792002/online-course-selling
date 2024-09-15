package vn.edu.likelion.utility;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.edu.likelion.entity.User;
import vn.edu.likelion.exception.ApiException;
import vn.edu.likelion.exception.CustomHttpStatus;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class AppConstant {
    public static final String SUBJECT_REGISTER = "Please verify your registration to continue";
    public static final String CONTENT_REGISTER = "<div style=\"font-size: 16px; letter-spacing: normal;\">Dear [[name]]," +
            "</div><div style=\"font-size: 16px; letter-spacing: normal;\"><i><br></i></div>" +
            "<div style=\"font-size: 16px; letter-spacing: normal;\">" +
            "<i>Click the link below to verify your registration:</i></div>" +
            "<div style=\"font-size: 16px; letter-spacing: normal;\"><i><br></i></div>" +
            "<a href=\"[[URL]]\" target=\"_self\" style=\"color: rgb(0, 123, 255); background-color: " +
            "transparent; font-size: 16px; letter-spacing: normal;\">VERIFY</a><div style=\"font-size: 16px;" +
            " letter-spacing: normal;\"><span style=\"font-size: 18px;\"><span style=\"font-size: 24px;\">" +
            "<span style=\"font-weight: bolder;\"><font color=\"#ff0000\"></font></span></span>" +
            "</span></div><div style=\"font-size: 16px; letter-spacing: normal;\"><br></div>" +
            "<div style=\"font-size: 16px; letter-spacing: normal;\">Thanks,</div>" +
            "<div style=\"font-size: 16px; letter-spacing: normal;\">The Tech Courses Team</div>";
    public static final String SUBJECT_RESET = "Here's the link to reset your password";
    public static final String CONTENT_RESET = "<p>Hello [[name]],</p>" +
            "<p>You have requested to reset your password.</p>" +
            "<p>Click the link below to change your password:</p>" +
            "<p><a href=\"[[URL]]\">Change my password</a></p>" +
            "<br>" +
            "<p>Ignore this email if you do remember your password, " +
            "or you have not made the request.</p>";

    public static String getEmailFromContextHolder(){
        var context = SecurityContextHolder.getContext();
        return context.getAuthentication().getName();
    }

    public static void sendEmail(User user, String url, String sub, String content){
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
            helper.setSubject(sub);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new ApiException(CustomHttpStatus.ERROR_SEND_EMAIL);
        }

        content = content.replace("[[name]]",user.getFullName());


        content = content.replace("[[URL]]", url);
        try {
            helper.setText(content,true);
        } catch (MessagingException e) {
            throw new ApiException(CustomHttpStatus.ERROR_SEND_EMAIL);
        }
        mailSender.send(message);
    }

    private AppConstant() {
        throw new IllegalStateException("Utility class");
    }

}
