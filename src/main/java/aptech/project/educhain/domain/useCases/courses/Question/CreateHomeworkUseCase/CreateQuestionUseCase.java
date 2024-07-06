package aptech.project.educhain.domain.useCases.courses.Question.CreateHomeworkUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Answers;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateQuestionUseCase implements Usecase<QuestionDTO, CreateQuestionParam> {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthUserRepository authUserRepository;

    @Autowired
    HomeworkRepository homeworkRepository;


    @Override
    public AppResult<QuestionDTO> execute(CreateQuestionParam params) {
        try {
            Question question = new Question();
            Homework homework = homeworkRepository.findById(params.getHomeworkId()).get();
            question.setHomework(homework);
            question.setQuestionText(params.getQuestionText());
            if (params.getCorrectAnswer() != null) {
                question.setCorrectAnswer(params.getAnswers().get(0));
            }
            Question savedQuestion = questionRepository.save(question);
            QuestionDTO questionDTO = modelMapper.map(savedQuestion, QuestionDTO.class);

            return AppResult.successResult(questionDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to create question: " + e.getMessage()));
        }
    }
}
