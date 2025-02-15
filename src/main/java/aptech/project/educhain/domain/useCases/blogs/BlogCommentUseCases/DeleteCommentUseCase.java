package aptech.project.educhain.domain.useCases.blogs.BlogCommentUseCases;

import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.repositories.blogs.BlogCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteCommentUseCase {
    @Autowired
    BlogCommentRepository blogCommentRepository;

    public boolean execute(Integer id) {
        try {
            BlogComment blogComment = blogCommentRepository.findById(id).get();
            if (blogComment != null){
                blogCommentRepository.deleteById(id);
                return true;
            }else{
                return false;
            }

        }catch(Exception ex){
            ex.getMessage();
        }
        return false;
    }
}
