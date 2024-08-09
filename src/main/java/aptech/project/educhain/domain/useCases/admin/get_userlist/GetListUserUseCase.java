package aptech.project.educhain.domain.useCases.admin.get_userlist;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class GetListUserUseCase implements Usecase<Page<UserDTO>,GetListUserParams> {

    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public AppResult<Page<UserDTO>> execute(GetListUserParams params) {
        try {
            Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
            Page<User> users = authUserRepository.findByNameContainingIgnoreCase(params.getEmailSearch(), pageable);

            List<UserDTO> userDTOList = users.getContent()
                    .stream()
                    .map(user -> {
                        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
//                        System.out.println("Mapped isActive: " + userDTO.getIsActive()); // Log to check value
                        return userDTO;
                    })
                    .collect(Collectors.toList());

            Page<UserDTO> userDTOPage = new PageImpl<>(userDTOList, pageable, users.getTotalElements());

            return AppResult.successResult(userDTOPage);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error getting users: " + e.getMessage()));
        }
    }
}
