package aptech.project.educhain.common.usecase;

public interface Usecase<SuccessType, NoParam> {
    public SuccessType execute(NoParam params);
}