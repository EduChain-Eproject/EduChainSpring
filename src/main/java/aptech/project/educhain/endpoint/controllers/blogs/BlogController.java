package aptech.project.educhain.endpoint.controllers.blogs;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.entities.blogs.UserBlogVote;
import aptech.project.educhain.domain.dtos.blogs.BlogCommentDTO;
import aptech.project.educhain.domain.dtos.blogs.UserBlogVoteDTO;
import aptech.project.educhain.endpoint.requests.blogs.CreateBlogReq;
import aptech.project.educhain.endpoint.requests.blogs.VoteRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.BlogCategory;
import aptech.project.educhain.data.serviceImpl.accounts.AuthService;
import aptech.project.educhain.data.serviceImpl.blogs.BlogCategoryService;
import aptech.project.educhain.data.serviceImpl.blogs.BlogService;
import aptech.project.educhain.data.serviceImpl.common.UploadPhotoService;
import aptech.project.educhain.domain.dtos.blogs.BlogDTO;
import aptech.project.educhain.domain.services.blogs.UserBlogVoteService;
import aptech.project.educhain.endpoint.requests.blogs.FilterBlogRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Blog")
@RestController
@CrossOrigin
@RequestMapping("/api/blog")
public class BlogController {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    BlogService service;

    @Autowired
    UserBlogVoteService userBlogVoteService;

    @Autowired
    UploadPhotoService uploadPhotoService;

    @Autowired
    AuthService userService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BlogCategoryService blogCategoryService;

    @Operation(summary = "Get all blog")
    @GetMapping("")
    public List<BlogDTO> findAll() {
        List<Blog> blogs = service.findAll();

        List<Blog> sortedBlogs = blogs.stream()
                .sorted(Comparator.comparing(Blog::getCreatedAt).reversed())
                .collect(Collectors.toList());

        return sortedBlogs.stream()
                .map(blog -> modelMapper.map(blog, BlogDTO.class))
                .collect(Collectors.toList());
    }
    

    private BlogCommentDTO mapChildComment(BlogComment comment, Integer blogId) {
        BlogCommentDTO dto = modelMapper.map(comment, BlogCommentDTO.class);
        dto.setBlogId(blogId); // Set the blogId inherited from the parent comment
        if (comment.getParentComment() != null) {
            dto.setParentCommentId(comment.getParentComment().getId());
        }
        // Recursively map child comments, if any
        List<BlogCommentDTO> childComments = comment.getReplies().stream()
                .map(child -> mapChildComment(child, blogId))
                .collect(Collectors.toList());
        dto.setReplies(childComments);
        return dto;
    }

    @Operation(summary = "Get 1 blog")
    @GetMapping("/{id}")
    public BlogDTO findOne(@PathVariable Integer id) {
        Blog blog = service.findOneBlog(id);

        BlogDTO blogDTO = modelMapper.map(blog, BlogDTO.class);
        List<BlogCommentDTO> commentDTOs = blog.getBlogComments().stream()
                .map(comment -> mapCommentToDTO(comment, blog.getId()))
                .collect(Collectors.toList());

        List<UserBlogVoteDTO> userBlogVoteDTOs = blog.getUserBlogVotes().stream()
                .map(userBlogVote -> {
                    UserBlogVoteDTO userBlogVoteDTO = modelMapper.map(userBlogVote, UserBlogVoteDTO.class);
                    userBlogVoteDTO.setBlogId(userBlogVote.getBlog().getId());
                    userBlogVoteDTO.setUserId(userBlogVote.getUser().getId());
                    return userBlogVoteDTO;
                })
                .collect(Collectors.toList());

        blogDTO.setBlogComments(commentDTOs);
        blogDTO.setUserBlogVotes(userBlogVoteDTOs);
        return blogDTO;
    }

    private BlogCommentDTO mapCommentToDTO(BlogComment comment, Integer blogId) {
        BlogCommentDTO dto = modelMapper.map(comment, BlogCommentDTO.class);
        dto.setBlogId(blogId);
        if (comment.getParentComment() != null) {
            dto.setParentCommentId(comment.getParentComment().getId());
        }
        List<BlogCommentDTO> childComments = comment.getReplies().stream()
                .map(child -> mapCommentToDTO(child, blogId))
                .collect(Collectors.toList());
        dto.setReplies(childComments);
        return dto;
    }

    @Operation(summary = "Add new blog")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@Valid @ModelAttribute CreateBlogReq req,BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            ApiError apiError = new ApiError(errors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = uploadPhotoService.uploadPhoto(req.getPhoto());
            Blog blog = new Blog();
            User user = userService.findUserById(req.getUserId());
            BlogCategory category = blogCategoryService.findBlogCategory(req.getBlogCategoryId());

            blog.setUser(user);
            blog.setTitle(req.getTitle());
            blog.setBlogCategory(category);
            blog.setBlogText(req.getBlogText());
            blog.setPhoto(fileName);

            Blog createdBlog = service.create(blog);

            BlogDTO createdBlogDTO = modelMapper.map(createdBlog, BlogDTO.class);

            return new ResponseEntity<>(createdBlogDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiError apiError = new ApiError("Error when creating blog");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

        }
    }

    @Operation(summary = "Vote")
    @PostMapping("/vote")
    public BlogDTO vote(
            @RequestBody VoteRequest rq) {
        Blog updatedBlog = userBlogVoteService.vote(rq.getUserId(), rq.getBlogId(), rq.getVote());
        BlogDTO blogDTO = modelMapper.map(updatedBlog, BlogDTO.class);
        List<UserBlogVoteDTO> userBlogVoteDTOs = updatedBlog.getUserBlogVotes().stream()
                .map(userBlogVote -> {
                    UserBlogVoteDTO userBlogVoteDTO = modelMapper.map(userBlogVote, UserBlogVoteDTO.class);
                    userBlogVoteDTO.setBlogId(userBlogVote.getBlog().getId());
                    userBlogVoteDTO.setUserId(userBlogVote.getUser().getId());
                    return userBlogVoteDTO;
                })
                .collect(Collectors.toList());
        blogDTO.setUserBlogVotes(userBlogVoteDTOs);
        return blogDTO;
    }

    @Operation(summary = "Find user blog vote")
    @GetMapping("/userBlogVote")
    public Boolean findUserBlogVote(
            @RequestParam Integer blogId,
            @RequestParam Integer userId) {
        UserBlogVote userBlogVote = userBlogVoteService.findUserBlogVote(userId, blogId);
        if (userBlogVote == null) {
            return false;
        }
        return true;
    }

    @Operation(summary = "Edit blog")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@Valid @ModelAttribute CreateBlogReq req, BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            ApiError apiError = new ApiError(errors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = uploadPhotoService.uploadPhoto(req.getPhoto());

            Blog blog = service.findOneBlog(req.getUserId());
            BlogCategory category = blogCategoryService.findBlogCategory(req.getBlogCategoryId());

            blog.setTitle(req.getTitle());
            blog.setBlogCategory(category);
            blog.setBlogText(req.getBlogText());

            String oldPhoto = blog.getPhoto();
            String photoFile = fileName != null ? fileName : oldPhoto;

            blog.setPhoto(photoFile);

            if (fileName != null) {
                Path path = Paths.get(uploadDir);
                Files.deleteIfExists(path.resolve(oldPhoto));
            }

            Blog updatedBlog = service.update(req.getUserId(), blog);

            BlogDTO updatedBlogDTO = modelMapper.map(updatedBlog, BlogDTO.class);

            return new ResponseEntity<>(updatedBlogDTO, HttpStatus.OK);
        } catch (Exception e) {
            ApiError apiError = new ApiError("Error when creating blog");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete blog")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    @GetMapping("/filter")
    public List<BlogDTO> filter(
            @RequestParam(required = false) String sortStrategy,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer[] categoryIdArray) {

        System.out.println(sortStrategy);
        System.out.println(keyword);
        System.out.println(Arrays.stream(categoryIdArray).toList());

        List<Blog> blogs = service.findAll();
        if (categoryIdArray != null && categoryIdArray.length > 0){
            blogs = service.findByCategory(blogs, categoryIdArray);

        }
        if (keyword != null || !keyword.isEmpty() || keyword != ""){
            blogs = service.search(blogs, keyword);

        }

        if (sortStrategy != null || !sortStrategy.isEmpty() || sortStrategy != "" ){
            blogs = service.sorting(blogs, service.getSortStrategy(sortStrategy));
        }
        return blogs.stream().map(blog -> modelMapper.map(blog, BlogDTO.class)).collect(Collectors.toList());
    }
}
