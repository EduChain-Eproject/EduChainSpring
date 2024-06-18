package aptech.project.educhain.data.serviceInterfaces.accounts;

public interface IEmailService {
    String templateEmail(String name, String link);
    void sendEmail(String to, String email);

}
