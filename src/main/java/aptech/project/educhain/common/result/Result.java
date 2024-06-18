package aptech.project.educhain.common.result;

public class Result<T, F> {
    private final T success;
    private final F failure;
    private final boolean isSuccess;

    protected Result(T success, F failure, boolean isSuccess) {
        this.success = success;
        this.failure = failure;
        this.isSuccess = isSuccess;
    }

    public static <T, F> Result<T, F> ofSuccess(T success) {
        return new Result<>(success, null, true);
    }

    public static <T, F> Result<T, F> ofFailure(F failure) {
        return new Result<>(null, failure, false);
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean isFailure() {
        return !isSuccess;
    }

    public T getSuccess() {
        return success;
    }

    public F getFailure() {
        return failure;
    }
}
