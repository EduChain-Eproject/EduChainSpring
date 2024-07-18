package aptech.project.educhain.domain.useCases.blogs.BlogCommentUseCases;

import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.repositories.blogs.BlogCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindCommentUseCase {
    @Autowired
    BlogCommentRepository blogCommentRepository;

    public BlogComment execute(Integer id) {
        try {
            return blogCommentRepository.findById(id).get();
        }catch(Exception ex){
            ex.getMessage();
        }
        return null;
    }
}
