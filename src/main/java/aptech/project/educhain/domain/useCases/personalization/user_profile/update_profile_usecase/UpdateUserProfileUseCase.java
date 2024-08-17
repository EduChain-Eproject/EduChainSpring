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
            // Tìm user cần update dựa trên ID
            User findUser = authUserRepository.findUserWithId(params.getId());

            // Giữ lại avatarPath cũ
            String oldAvatarPath = findUser.getAvatarPath();

            // Cập update trường không null từ params vào findUser
            boolean isMapped = mapProfile(findUser, params);
            if (!isMapped) {
                return AppResult.failureResult(new Failure("Failed to map profile fields."));
            }

            if (params.getAvatarFile() != null) {
                // Nếu avatarFile !null, upload ảnh mới lên cloudinary và cập nhật avatarPath
                String newPath = cloudinarySerivce.upload(params.getAvatarFile());

                // Xóa ảnh cũ trên cloudinary nếu không phải là ảnh mặc định
                if (!oldAvatarPath.equals(defaultAvatar)) {
                    cloudinarySerivce.deleteImageByUrl(oldAvatarPath);
                }

                findUser.setAvatarPath(newPath);
            } else {
                // Nếu avatarFile là null, giữ nguyên giá trị cũ
                findUser.setAvatarPath(oldAvatarPath);
            }

            // Lưu lại user đã được update vào database
            User updatedUser = authUserRepository.save(findUser);

            // Map User đã update thành UserProfileDTO
            UserProfileDTO userProfileDTO = modelMapper.map(updatedUser, UserProfileDTO.class);

            return AppResult.successResult(userProfileDTO);

        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to update profile: " + e.getMessage()));
        }
    }



    public boolean mapProfile(User user, UpdateUserProfileParam params) {
        Field[] paramFields = params.getClass().getDeclaredFields();
        try {
            for (Field paramField : paramFields) {
                paramField.setAccessible(true);
                Object sourceValue = paramField.get(params);

                // Chỉ xử lý các trường không phải là id và avatarFile, và không null
                if ((sourceValue != null && !(sourceValue instanceof String && ((String) sourceValue).isEmpty()) && !paramField.getName().equals("id") && !paramField.getName().equals("avatarFile"))) {
                    // Tìm trường tương ứng trong đối tượng user
                    try {
                        Field userField = user.getClass().getDeclaredField(paramField.getName());
                        userField.setAccessible(true);
                        userField.set(user, sourceValue);
                    } catch (NoSuchFieldException e) {
                        // Trường không tồn tại trong đối tượng user
                        System.err.println("Field " + paramField.getName() + " not found in User class.");
                    }
                }
            }
            return true;
        } catch (IllegalAccessException e) {
            System.err.println("Error accessing fields: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error during mapping: " + e.getMessage());
            return false;
        }
    }
}

