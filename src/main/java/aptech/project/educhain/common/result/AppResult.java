package aptech.project.educhain.common.result;

public class AppResult<T> extends Result<T, Failure> {

    private AppResult(T success, Failure failure, boolean isSuccess) {
        super(success, failure, isSuccess);
    }

    public static <T> AppResult<T> successResult(T success) {
        return new AppResult<>(success, null, true);
    }

    public static <T> AppResult<T> failureResult(Failure failure) {
        return new AppResult<>(null, failure, false);
    }
}
