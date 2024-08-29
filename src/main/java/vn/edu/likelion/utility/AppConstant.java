package vn.edu.likelion.utility;

import org.springframework.security.core.context.SecurityContextHolder;

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

    public static String getEmailFromContextHolder(){
        var context = SecurityContextHolder.getContext();
        return context.getAuthentication().getName();
    }

    private AppConstant() {
        throw new IllegalStateException("Utility class");
    }

}
