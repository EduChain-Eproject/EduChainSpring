package aptech.project.educhain.domain.useCases.personalization.user_homework.list_userhomework;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.UserHomework;
import aptech.project.educhain.data.repositories.courses.UserHomeworkRepository;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ListUserHomeworkUseCase implements Usecase<Page<UserHomeworkDTO>, ListUserHomeworkParams> {

    @Autowired
    UserHomeworkRepository userHomeworkRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppResult<Page<UserHomeworkDTO>> execute(ListUserHomeworkParams params) {
        try {
            Pageable pageable = PageRequest.of(params.getPage(), params.getSize());
            Page<UserHomework> userHomeworkPage = userHomeworkRepository.findByUserId(params.getUserId(), pageable);
            Page<UserHomeworkDTO> userHomeworkDtoPage = userHomeworkPage.map(userHomework -> {
                UserHomeworkDTO userHomeworkDTO = modelMapper.map(userHomework, UserHomeworkDTO.class);
                userHomeworkDTO.setUserDto(modelMapper.map(userHomework.getUser(), UserDTO.class));
                userHomeworkDTO.setHomeworkDto(modelMapper.map(userHomework.getHomework(), HomeworkDTO.class));
                return userHomeworkDTO;
            });


            return AppResult.successResult(userHomeworkDtoPage);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to retrieve user homeworks: " + e.getMessage()));
        }
    }

}
