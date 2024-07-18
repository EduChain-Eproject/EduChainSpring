package aptech.project.educhain.domain.useCases.blogs.BlogCommentUseCases;

import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.domain.dtos.blogs.BlogCommentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapChildCommentUseCase {
    @Autowired
    ModelMapper modelMapper;

    public BlogCommentDTO execute(BlogComment comment, Integer blogId) {
        BlogCommentDTO dto = modelMapper.map(comment, BlogCommentDTO.class);
        dto.setBlogId(blogId); // Set the blogId inherited from the parent comment
        if (comment.getParentComment() != null) {
            dto.setParentCommentId(comment.getParentComment().getId());
        }
        List<BlogCommentDTO> childComments = comment.getReplies().stream()
                .map(child -> execute(child, blogId))
                .collect(Collectors.toList());
        dto.setReplies(childComments);
        return dto;
    }
}
