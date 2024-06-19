package aptech.project.educhain.common.usecase;

import aptech.project.educhain.common.result.AppResult;

public interface Usecase<SuccessType, NoParam> {
    public AppResult<SuccessType> execute(NoParam params);
}