package aptech.project.educhain.domain.useCases.blogs.BlogCommentUseCases;

import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.repositories.blogs.BlogCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EditCommentUseCase {
    @Autowired
    BlogCommentRepository blogCommentRepository;

    public BlogComment execute(Integer id, BlogComment blogComment) {
        try {
            BlogComment comment = blogCommentRepository.findById(id).get();
            if (comment != null) {
                comment.setText(blogComment.getText());
                return blogCommentRepository.save(comment);
            }
        }catch(Exception ex){
            ex.getMessage();
        }
        return null;
    }
}
