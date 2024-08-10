package aptech.project.educhain.domain.services.accounts;

public interface IEmailService {
    String templateEmail(String name, Integer code);
    void sendEmail(String to, String email);
    String templateResetPassword(String name, Integer code);
}
