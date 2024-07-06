package aptech.project.educhain.domain.services.accounts;

public interface IEmailService {
    String templateEmail(String name, String link);

    void sendEmail(String to, String email);
    String templateResetPassword(String name, String link);
}
