package aptech.project.educhain.domain.dtos.courses;

import java.util.List;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import lombok.Data;

@Data
    public class HomeworkDTO {
        private Integer id;
        private String title;
        private String description;

        private Integer userID;
        private Integer lessonID;

        private UserDTO userDto;
        private LessonDTO lessonDto;
        private List<QuestionDTO> questionDtos;
        private List<UserHomeworkDTO> userHomeworkDtos;
        private List<AwardDTO> userAwardDtos;

        public void mergeUserAnswersToQuestions(List<UserAnswerDTO> userAnswers) {
            questionDtos = questionDtos.stream().map(q -> {
                for (UserAnswerDTO userAnswerDTO : userAnswers) {
                    if (userAnswerDTO.getQuestionId() == q.getId()) {
                        q.setCurrentUserAnswerDto(
                                userAnswerDTO);
                    }
                }
                return q;
            }).toList();
        }

        public void mergeAwardsToUserHomeworks(List<AwardDTO> awardDtos) {
            userHomeworkDtos = userHomeworkDtos.stream().map(uh -> {
                for (AwardDTO award : awardDtos) {
                    if (award.getHomeworkDto().getId() == uh.getHomeworkDto().getId()) {
                        uh.setUserAwardDto(award);
                    }
                }
                return uh;
            }).toList();
        }

        public void mergeAwardsToUserHomeworks() {
            userHomeworkDtos = userHomeworkDtos.stream().map(uh -> {
                for (AwardDTO award : userAwardDtos) {
                    if (award.getHomeworkDtoId() == uh.getHomeworkDtoId()) {
                        uh.setUserAwardDto(award);
                    }
                }
                return uh;
            }).toList();
        }
    }
