package aptech.project.educhain.data.serviceImpl.admin;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.services.admin.IAdmin;
import aptech.project.educhain.domain.useCases.admin.block_or_unblock.BlockOrUnBlockParams;
import aptech.project.educhain.domain.useCases.admin.block_or_unblock.BlockOrUnBlockUseCase;
import aptech.project.educhain.domain.useCases.admin.get_userlist.GetListUserParams;
import aptech.project.educhain.domain.useCases.admin.get_userlist.GetListUserUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements IAdmin {

    @Autowired
    GetListUserUseCase getListUserUseCase;
    @Autowired
    BlockOrUnBlockUseCase blockOrUnBlockUseCase;
    @Override
    public AppResult<Page<UserDTO>> getListUser(GetListUserParams params) {
        return getListUserUseCase.execute(params);
    }

    @Override
    public AppResult<Boolean> blocAndUnBlock(BlockOrUnBlockParams params) {
        return blockOrUnBlockUseCase.execute(params);
    }
}
