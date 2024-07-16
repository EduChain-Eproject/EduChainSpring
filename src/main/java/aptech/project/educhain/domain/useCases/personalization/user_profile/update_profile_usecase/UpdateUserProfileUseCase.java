package aptech.project.educhain.domain.useCases.personalization.user_profile.update_profile_usecase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.serviceImpl.cloudinary.CloudinarySerivce;
import aptech.project.educhain.domain.dtos.UserProfile.UserProfileDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class UpdateUserProfileUseCase implements Usecase<UserProfileDTO, UpdateUserProfileParam> {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AuthUserRepository authUserRepository;
    @Autowired
    CloudinarySerivce cloudinarySerivce;
    @Value("${base.url.default.avatar}")
    private String defaultAvatar;

    @Override
    @Transactional
    public AppResult<UserProfileDTO> execute(UpdateUserProfileParam params) {
        try {
            User findUser = authUserRepository.findUserWithId(params.getId());
            String path = cloudinarySerivce.upload(params.getAvatarFile());
            if( !mapProfile(findUser,params)){
                cloudinarySerivce.delete(path);
                return AppResult.failureResult(new Failure("Error while mapping to account"));
            }
            if (!findUser.getAvatarPath().equals(defaultAvatar)) {
                cloudinarySerivce.deleteImageByUrl(findUser.getAvatarPath());
            }
            findUser.setAvatarPath(path);
            User newUser =  authUserRepository.save(findUser);
            UserProfileDTO userProfileDTO = modelMapper.map(newUser, UserProfileDTO.class);
            return AppResult.successResult(userProfileDTO);

        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to create update profile: " + e.getMessage()));
        }
    }

    public boolean mapProfile(User user, UpdateUserProfileParam params) {
        Field[] fields = params.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object sourceValue = field.get(params);
                if (sourceValue != null && !field.getName().equals("id") && !field.getName().equals("avatarFile")) {
                    Field targetField = user.getClass().getDeclaredField(field.getName());
                    targetField.setAccessible(true);
                    targetField.set(user, sourceValue);
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error during mapping: " + e.getMessage());
            return false;
        }
    }


}

