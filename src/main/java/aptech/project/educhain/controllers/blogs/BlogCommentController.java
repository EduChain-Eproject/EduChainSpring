package aptech.project.educhain.controllers.blogs;

import aptech.project.educhain.dto.blogs.BlogCommentDTO;
import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.models.blogs.Blog;
import aptech.project.educhain.models.blogs.BlogComment;
import aptech.project.educhain.services.auth.AuthService;
import aptech.project.educhain.services.blogs.BlogCommentService;
import aptech.project.educhain.services.blogs.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Operation(summary = "Get all comment")
    @GetMapping("")
    private List<BlogCommentDTO> findAll(){
        List<BlogComment> comments = blogCommentService.findAll();
        return comments.stream().map(comment -> modelMapper.map(comment, BlogCommentDTO.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Get 1 category")
    @GetMapping("{id}")
    private BlogCommentDTO findOne(@PathVariable Integer id){
        BlogComment comment = blogCommentService.findComment(id);
        return modelMapper.map(comment, BlogCommentDTO.class);
    }

    @Operation(summary = "Get comments by blog")
    @GetMapping("/blog/{id}")
    private List<BlogCommentDTO> findByBlog(@PathVariable Integer id){
        List<BlogComment> comments = blogCommentService.getByBlog(id);
        return comments.stream().map(comment -> modelMapper.map(comment, BlogCommentDTO.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Add new comment")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@RequestParam("text") String text,
            @RequestParam("userId") Integer userId,
            @RequestParam("parentComment") String commentId,
            @RequestParam("blogId") Integer blogId,
            @RequestParam("photo") MultipartFile photo){
//        Map<String, String> errors = service.validateFields(title, userId, blogCategoryId, blogText);
//        if (!errors.isEmpty()) {
//            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//        }

        try {
            String fileName = blogService.uploadPhoto(uploadDir, photo);

            BlogComment comment = new BlogComment();
            User user = userService.findUserById(userId);
            Blog blog = blogService.findBlog(blogId);
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
            comment.setPhoto(fileName);

            BlogComment createdComment = blogCommentService.create(comment);

            BlogCommentDTO createdBlogCommentDTO = modelMapper.map(createdComment, BlogCommentDTO.class);

            return new ResponseEntity<>(createdBlogCommentDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
