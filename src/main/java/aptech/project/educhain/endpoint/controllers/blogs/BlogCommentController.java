package aptech.project.educhain.endpoint.controllers.blogs;

import java.util.List;
import java.util.stream.Collectors;

import aptech.project.educhain.domain.dtos.accounts.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.serviceImpl.accounts.AuthService;
import aptech.project.educhain.data.serviceImpl.blogs.BlogCommentService;
import aptech.project.educhain.data.serviceImpl.blogs.BlogService;
import aptech.project.educhain.data.serviceImpl.common.UploadPhotoService;
import aptech.project.educhain.domain.dtos.blogs.BlogCommentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Blog comment")
@RestController
@RequestMapping("/api/blog_comment")
public class BlogCommentController {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    BlogCommentService blogCommentService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BlogService blogService;

    @Autowired
    AuthService userService;

    @Operation(summary = "Get 1 comment")
    @GetMapping("{id}")
    private BlogCommentDTO findOne(@PathVariable Integer id) {
        BlogComment comment = blogCommentService.findComment(id);
        return modelMapper.map(comment, BlogCommentDTO.class);
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity<List<BlogCommentDTO>> findByBlog(@PathVariable Integer id) {
        try {
            List<BlogComment> comments = blogCommentService.findCommentByBlog(id);
            if (comments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<BlogCommentDTO> commentDTOs = comments.stream().map(comment -> {
                BlogCommentDTO dto = modelMapper.map(comment, BlogCommentDTO.class);
                if (comment.getBlog() != null) {
                    dto.setBlogId(comment.getBlog().getId());
                }
                if (comment.getParentComment() != null) {
                    dto.setParentCommentId(comment.getParentComment().getId());
                }
                // Map child comments
                List<BlogCommentDTO> childComments = comment.getReplies().stream()
                        .map(child -> blogCommentService.mapChildCommentService(child, dto.getBlogId()))
                        .collect(Collectors.toList());
                dto.setReplies(childComments);
                return dto;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(commentDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @Operation(summary = "Add new comment")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@RequestParam("text") String text,
            @RequestParam("userId") Integer userId,
            @RequestParam("parentComment") String commentId,
            @RequestParam("blogId") Integer blogId) {
        try {
            BlogComment comment = new BlogComment();
            User user = userService.findUserById(userId);
            Blog blog = blogService.findOneBlog(blogId);
            BlogComment parent = null;

            if (commentId != null && !commentId.equals("null") && !commentId.isEmpty()) {
                try {
                    parent = blogCommentService.findComment(Integer.parseInt(commentId));
                } catch (NumberFormatException e) {
                    return new ResponseEntity<>("Invalid comment ID", HttpStatus.BAD_REQUEST);
                }
            }

            comment.setUser(user);
            comment.setBlog(blog);
            comment.setText(text);
            comment.setParentComment(parent);

            BlogComment createdComment = blogCommentService.addComment(comment);

            BlogCommentDTO createdBlogCommentDTO = modelMapper.map(createdComment, BlogCommentDTO.class);

            return new ResponseEntity<>(createdBlogCommentDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
