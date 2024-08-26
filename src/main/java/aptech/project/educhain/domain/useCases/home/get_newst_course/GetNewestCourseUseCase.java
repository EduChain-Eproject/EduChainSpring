package aptech.project.educhain.domain.useCases.home.get_newst_course;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import aptech.project.educhain.domain.dtos.blogs.BlogDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class GetNewestCourseUseCase implements Usecase<List<BlogDTO>, Void> {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    BlogRepository blogRepository;

    @Override
    public AppResult<List<BlogDTO>> execute(Void params) {
                try{
                    Pageable page = PageRequest.of(0,3);
            var newestBlogs = blogRepository.findNewestBlogs(page);
            List<BlogDTO> blogDTOs = newestBlogs.stream().map(blog -> modelMapper.map(blog,BlogDTO.class)).toList();
            return AppResult.successResult(blogDTOs);
        }
        catch (Exception e){
            return AppResult.failureResult(new Failure("Error getting newest blog"+e.getMessage()));
        }
    }
}
