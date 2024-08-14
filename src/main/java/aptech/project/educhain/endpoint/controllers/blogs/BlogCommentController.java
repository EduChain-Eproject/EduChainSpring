package aptech.project.educhain.endpoint.controllers.blogs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import aptech.project.educhain.common.result.ApiError;

import aptech.project.educhain.endpoint.requests.blogs.BlogCommentReq;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.serviceImpl.accounts.AuthService;
import aptech.project.educhain.data.serviceImpl.blogs.BlogCommentService;
import aptech.project.educhain.data.serviceImpl.blogs.BlogService;
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
//    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody BlogCommentReq req, BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            ApiError apiError = new ApiError(errors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        try {
            BlogComment comment = new BlogComment();
            User user = userService.findUserById(req.getUserId());
            Blog blog = blogService.findOneBlog(req.getBlogId());
            BlogComment parent = null;

            if (req.getCommentId() != null) {
                try {
                    parent = blogCommentService.findComment(req.getCommentId());
                } catch (NumberFormatException e) {
                    return new ResponseEntity<>("Invalid comment ID", HttpStatus.BAD_REQUEST);
                }
            }

            comment.setUser(user);
            comment.setBlog(blog);
            comment.setText(req.getText());
            comment.setParentComment(parent);

            BlogComment createdComment = blogCommentService.addComment(comment);

            BlogCommentDTO createdBlogCommentDTO = modelMapper.map(createdComment, BlogCommentDTO.class);

            return new ResponseEntity<>(createdBlogCommentDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiError apiError = new ApiError("Error when add comment");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
            }
    }

    @Operation(summary = "update comment")
//    @PostMapping(value = "update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PutMapping("update")
    public ResponseEntity<?> update(@Valid @RequestBody BlogCommentReq req, BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            ApiError apiError = new ApiError(errors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        try {
            BlogComment existingComment = blogCommentService.findComment(req.getCommentId());
            if (existingComment == null) {
                return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
            }

            User user = userService.findUserById(req.getUserId());
            Blog blog = blogService.findOneBlog(req.getBlogId());
            BlogComment parent = null;

            if (req.getCommentId() != null) {
                try {
                    parent = blogCommentService.findComment(req.getCommentId());
                } catch (NumberFormatException e) {
                    return new ResponseEntity<>("Invalid comment ID", HttpStatus.BAD_REQUEST);
                }
            }

            existingComment.setUser(user);
            existingComment.setBlog(blog);
            existingComment.setText(req.getText());
            existingComment.setParentComment(parent);

            BlogComment updatedComment = blogCommentService.editComment(req.getCommentId(),existingComment.getParentComment());

            BlogCommentDTO updatedBlogCommentDTO = modelMapper.map(updatedComment, BlogCommentDTO.class);

            return new ResponseEntity<>(updatedBlogCommentDTO, HttpStatus.OK);
        } catch (Exception e) {
            ApiError apiError = new ApiError("Error when edit comment");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
    }
}
