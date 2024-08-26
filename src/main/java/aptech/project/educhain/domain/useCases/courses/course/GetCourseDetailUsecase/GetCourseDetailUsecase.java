package aptech.project.educhain.domain.useCases.courses.course.GetCourseDetailUsecase;

import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.repositories.accounts.UserInterestRepository;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.data.repositories.courses.UserHomeworkRepository;
import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import aptech.project.educhain.domain.dtos.courses.CategoryDTO;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;
import aptech.project.educhain.domain.dtos.courses.CourseFeedbackDTO;
import aptech.project.educhain.domain.dtos.courses.LessonDTO;
import aptech.project.educhain.domain.dtos.courses.UserCourseDTO;

@Component
public class GetCourseDetailUsecase implements Usecase<CourseDTO, GetCourseDetailParams> {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserHomeworkRepository userHomeworkRepository;

    @Autowired
    UserInterestRepository userInterestRepo;

    @Override
    public AppResult<CourseDTO> execute(GetCourseDetailParams params) {
        try {
            Optional<Course> courseOptional = courseRepository.findById(params.getCourseId());

            if (courseOptional.isPresent()) {

                Course course = courseOptional.get();

                CourseDTO courseDTO = modelMapper.map(course, CourseDTO.class);

                courseDTO.setCategoryDtos(course.getCategories().stream()
                        .map(category -> modelMapper.map(category, CategoryDTO.class))
                        .collect(Collectors.toList()));

                courseDTO.setChapterDtos(course.getChapters().stream()
                        .map(chapter -> {
                            ChapterDTO dto = modelMapper.map(chapter, ChapterDTO.class);
                            dto.setLessonDtos(
                                    chapter
                                            .getLessons()
                                            .stream()
                                            .map(ls -> {
                                                var lsDto = modelMapper.map(ls, LessonDTO.class);

                                                if (params.getUserId() != null) {
                                                    ls.getHomeworks().stream().forEach((homework) -> {
                                                        var uh = userHomeworkRepository.findByUserIdAndHomeworkId(
                                                                params.getUserId(), homework.getId());
                                                        if(uh.isPresent()){

                                                        if (uh.get().getProgress() == 100) {
                                                            lsDto.setCurrentUserFinished(true);
                                                        } else {
                                                            lsDto.setCurrentUserFinished(false);
                                                        }
                                                        }

                                                    });
                                                }

                                                return lsDto;
                                            })
                                            .toList());
                            return dto;
                        })
                        .collect(Collectors.toList()));

                Integer lessonIdToLearn = courseDTO.getChapterDtos().stream()
                        .flatMap(chapter -> chapter.getLessonDtos().stream())
                        .filter(lesson -> !lesson.isCurrentUserFinished())
                        .findFirst()
                        .map(LessonDTO::getId)
                        .orElse(null);
                courseDTO.setLessonIdTolearn(lessonIdToLearn);

                if (params.getUserId() != null) {
                    var interest = userInterestRepo.findByCourseIdAndUserId(params.getCourseId(), params.getUserId());

                    courseDTO.setCurrentUserInterested(interest != null);
                }

                courseDTO.setParticipatedUserDtos(course.getParticipatedUsers().stream()
                        .map(student -> {
                            UserCourseDTO dto = modelMapper.map(student, UserCourseDTO.class);
                            dto.setUserDto(modelMapper.map(student.getUser(), UserDTO.class));
                            return dto;
                        })
                        .collect(Collectors.toList()));

                courseDTO.setCourseFeedbackDtos(course.getCourseFeedbacks().stream()
                        .map(fb -> {
                            CourseFeedbackDTO dto = modelMapper.map(fb, CourseFeedbackDTO.class);
                            dto.setUserDto(modelMapper.map(fb.getUser(), UserDTO.class));
                            return dto;
                        })
                        .collect(Collectors.toList()));

                return AppResult.successResult(courseDTO);
            } else {
                return AppResult.failureResult(new Failure("Course not found with ID: " + params.getCourseId()));
            }
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error getting course details: " + e.getMessage()));
        }
    }
}
