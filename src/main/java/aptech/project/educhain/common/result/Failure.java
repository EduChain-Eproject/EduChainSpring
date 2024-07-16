package aptech.project.educhain.common.result;

public class Failure {
    private final String message;

    public Failure(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
