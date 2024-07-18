package aptech.project.educhain.domain.useCases.blogs.BlogCommentUseCases;

import aptech.project.educhain.data.repositories.blogs.BlogCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteCommentUseCase {
    @Autowired
    BlogCommentRepository blogCommentRepository;

    public boolean execute(Integer id) {
        try {
            blogCommentRepository.deleteById(id);
            return true;
        }catch(Exception ex){
            ex.getMessage();
        }
        return false;
    }
}
