package aptech.project.educhain.domain.useCases.courses.Question.CreateQuestionUseCase;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Answer;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.entities.courses.Question;
import aptech.project.educhain.data.repositories.accounts.AuthUserRepository;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.data.repositories.courses.QuestionRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;

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

            if (params.getAnswerTexts().size() > 4) {
                return AppResult
                        .failureResult(new Failure("Failed to create question: maximum number of answers is 4"));
            } else if (params.getAnswerTexts().size() > 0) {
                List<Answer> answers = new ArrayList<>();
                for (int i = 0; i < params.getAnswerTexts().size(); i++) {
                    var answer = new Answer();
                    answer.setQuestion(question);
                    answer.setAnswerText(params.getAnswerTexts().get(i));
                    answers.add(answer);
                }
                question.setAnswers(answers);

                if (params.getCorrectAnswerIndex() != -1) {
                    question.setCorrectAnswer(question.getAnswers().get(params.getCorrectAnswerIndex()));
                }
            }

            Question savedQuestion = questionRepository.save(question);

            QuestionDTO questionDTO = modelMapper.map(savedQuestion, QuestionDTO.class);
            questionDTO.setHomeworkId(savedQuestion.getHomework().getId());
            questionDTO.setCorrectAnswerId(savedQuestion.getCorrectAnswer().getId());

            questionDTO.setAnswerDtos(savedQuestion.getAnswers().stream().map(answers -> {
                AnswerDTO answerDTO = modelMapper.map(answers, AnswerDTO.class);
                answerDTO.setQuestionId(answers.getQuestion().getId());
                answerDTO.setId(answers.getId());
                return answerDTO;
            }).toList());

            return AppResult.successResult(questionDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to create question: " + e.getMessage()));
        }
    }
}
