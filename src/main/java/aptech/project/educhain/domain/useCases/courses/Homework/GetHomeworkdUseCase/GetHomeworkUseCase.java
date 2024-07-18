package aptech.project.educhain.domain.useCases.courses.Homework.GetHomeworkdUseCase;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Homework;
import aptech.project.educhain.data.repositories.courses.HomeworkRepository;
import aptech.project.educhain.domain.dtos.courses.AnswerDTO;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.courses.HomeworkDTO;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;
import aptech.project.educhain.domain.dtos.courses.QuestionDTO;

@Component
public class GetHomeworkUseCase implements Usecase<HomeworkDTO, GetHomeworkParam> {
    @Autowired
    HomeworkRepository homeworkRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public AppResult<HomeworkDTO> execute(GetHomeworkParam params) {
        try {
            Homework homework = homeworkRepository.findById(params.getId()).get();

            HomeworkDTO homeworkDTO = modelMapper.map(homework, HomeworkDTO.class);

            homeworkDTO.setLessonID(homework.getLesson().getId());

            homeworkDTO.setLessonDto(
                    modelMapper.map(homework.getLesson(), LessonDTO.class));

            homeworkDTO.getLessonDto().setChapterDto(
                    modelMapper.map(homework.getLesson().getChapter(), ChapterDTO.class));

            homeworkDTO.getLessonDto().getChapterDto().setCourseDto(
                    modelMapper.map(homework.getLesson().getChapter().getCourse(), CourseDTO.class));

            homeworkDTO.setQuestionDtos(
                    homework.getQuestions().stream().map(
                            q -> {
                                QuestionDTO qDto = modelMapper.map(q, QuestionDTO.class);

                                qDto.setAnswerDtos(
                                        q.getAnswers().stream().map(
                                                a -> modelMapper.map(a, AnswerDTO.class)).toList());

                                qDto.setCorrectAnswerDto(modelMapper.map(q.getCorrectAnswer(), AnswerDTO.class));

                                return qDto;
                            }).toList());

            // List<QuestionDTO> questionDTOList = homework
            // .getQuestions()
            // .stream()
            // .map(question -> {
            // QuestionDTO dto = modelMapper.map(question, QuestionDTO.class);
            // if (question.getCorrectAnswer() != null) {
            // dto.setCorrectAnswerId(question.getCorrectAnswer().getId());
            // }

            // List<Answers> answersList = question.getAnswers().stream().toList();
            // List<AnswerDTO> answerDTOList = answersList.stream().map(answers -> {
            // AnswerDTO answerDTO = modelMapper.map(answers, AnswerDTO.class);
            // answerDTO.setQuestionId(answers.getQuestion().getId());
            // answerDTO.setId(answers.getId());
            // return answerDTO;
            // }).toList();

            // dto.setAnswerDtos(answerDTOList);

            // return dto;
            // }).toList();

            // homeworkDTO.setQuestionDtos(questionDTOList);

            return AppResult.successResult(homeworkDTO);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get homework details: " + e.getMessage()));
        }
    }

}
