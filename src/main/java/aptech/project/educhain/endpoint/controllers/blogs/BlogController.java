package aptech.project.educhain.endpoint.controllers.blogs;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.entities.blogs.UserBlogVote;
import aptech.project.educhain.domain.dtos.blogs.BlogCommentDTO;
import aptech.project.educhain.domain.dtos.blogs.UserBlogVoteDTO;
import aptech.project.educhain.endpoint.requests.blogs.CreateBlogRequest;
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
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@Valid @ModelAttribute CreateBlogRequest createBlogRequest, BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = uploadPhotoService.uploadPhoto(createBlogRequest.getPhoto());
            Blog blog = new Blog();
            User user = userService.findUserById(createBlogRequest.getUserId());
            BlogCategory category = blogCategoryService.findBlogCategory(createBlogRequest.getBlogCategoryId());

            blog.setUser(user);
            blog.setTitle(createBlogRequest.getTitle());
            blog.setBlogCategory(category);
            blog.setBlogText(createBlogRequest.getBlogText());
            blog.setPhoto(fileName);

            Blog createdBlog = service.create(blog);

            BlogDTO createdBlogDTO = modelMapper.map(createdBlog, BlogDTO.class);

            return new ResponseEntity<>(createdBlogDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestParam("title") String title,
            @RequestParam("blogCategoryId") Integer blogCategoryId, @RequestParam("blogText") String blogText,
            @RequestParam("photo") MultipartFile photo) {
        Map<String, String> errors = service.validateFieldsUpdate(title, blogCategoryId, blogText);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = uploadPhotoService.uploadPhoto(photo);

            Blog blog = service.findOneBlog(id);
            BlogCategory category = blogCategoryService.findBlogCategory(blogCategoryId);

            blog.setTitle(title);
            blog.setBlogCategory(category);
            blog.setBlogText(blogText);

            String oldPhoto = blog.getPhoto();
            String photoFile = fileName != null ? fileName : oldPhoto;

            blog.setPhoto(photoFile);

            if (fileName != null) {
                Path path = Paths.get(uploadDir);
                Files.deleteIfExists(path.resolve(oldPhoto));
            }

            Blog updatedBlog = service.update(id, blog);

            BlogDTO updatedBlogDTO = modelMapper.map(updatedBlog, BlogDTO.class);

            return new ResponseEntity<>(updatedBlogDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete blog")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return service.delete(id);
    }

    @Operation(summary = "Filter blogs")
    @PostMapping("/filter")
    public List<BlogDTO> filter(@RequestBody FilterBlogRequest request) {
        System.out.println(request.getSortStrategy());
        System.out.println(request.getKeyword());

        // Check if categoryIdArray is null before processing
        if (request.getCategoryIdArray() != null) {
            System.out.println(Arrays.stream(request.getCategoryIdArray()).toList());
        } else {
            System.out.println("categoryIdArray is null");
        }

        List<Blog> blogs = service.findAll();

        if (request.getCategoryIdArray() != null && request.getCategoryIdArray().length > 0) {
            blogs = service.findByCategory(blogs, request.getCategoryIdArray());
        }

        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            blogs = service.search(blogs, request.getKeyword());
        }

        if (request.getSortStrategy() != null && !request.getSortStrategy().isEmpty()) {
            blogs = service.sorting(blogs, service.getSortStrategy(request.getSortStrategy()));
        }

        return blogs.stream().map(blog -> modelMapper.map(blog, BlogDTO.class)).collect(Collectors.toList());
    }


}
