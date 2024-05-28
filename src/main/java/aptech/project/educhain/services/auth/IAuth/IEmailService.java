package aptech.project.educhain.services.auth.IAuth;

import org.springframework.mail.javamail.JavaMailSender;

public interface IEmailService {
    String templateEmail(String name, String link);
    void sendEmail(String to, String email);

}
