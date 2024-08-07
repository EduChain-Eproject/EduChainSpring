package aptech.project.educhain.domain.useCases.courses.course.SearchCoursesUseCase;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.courses.CourseStatus;
import aptech.project.educhain.data.repositories.courses.CourseRepository;
import aptech.project.educhain.domain.dtos.courses.CourseDTO;

@Component
public class SearchCoursesUseCase implements Usecase<Page<CourseDTO>, CourseSearchParams> {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppResult<Page<CourseDTO>> execute(CourseSearchParams request) {
        try {
            Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(request.getSortBy()));
            Page<Course> coursePage;

            if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
                coursePage = courseRepository.findByCategoryIdsAndSearch(request.getCategoryIds(), request.getSearch(),
                        CourseStatus.APPROVED,
                        pageable);
            } else {
                coursePage = courseRepository.findBySearch(request.getSearch(), pageable,
                        request.getStatus());
            }

            Page<CourseDTO> courseDTOPage = coursePage.map(course -> modelMapper.map(course, CourseDTO.class));

            return AppResult.successResult(courseDTOPage);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get courses: " + e.getMessage()));
        }
    }
}
