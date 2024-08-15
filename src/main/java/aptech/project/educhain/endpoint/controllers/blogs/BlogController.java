package aptech.project.educhain.endpoint.controllers.blogs;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import aptech.project.educhain.common.result.ApiError;
import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.entities.blogs.UserBlogVote;
import aptech.project.educhain.domain.dtos.blogs.BlogCommentDTO;
import aptech.project.educhain.domain.dtos.blogs.UserBlogVoteDTO;
import aptech.project.educhain.domain.dtos.payment.OrderDTO;
import aptech.project.educhain.domain.services.accounts.IJwtService;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.BlogFilterUseCase.BlogFilterParam;
import aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindAllBlogUseCase.GetAllBlogParams;
import aptech.project.educhain.domain.useCases.payment.order.getAllOrderUseCase.GetAllOrderParams;
import aptech.project.educhain.endpoint.requests.blogs.CreateBlogReq;
import aptech.project.educhain.endpoint.requests.blogs.FindAllBlogRequest;
import aptech.project.educhain.endpoint.requests.blogs.VoteRequest;
import aptech.project.educhain.endpoint.requests.payment.order.OrderRequest;
import aptech.project.educhain.endpoint.responses.blogs.FilterBlogResponse;
import aptech.project.educhain.endpoint.responses.blogs.GetAllBlogResponse;
import aptech.project.educhain.endpoint.responses.payment.order.GetOrderResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
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
    BlogService blogService;

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

    @Autowired
    IJwtService iJwtService;

    @Operation(summary = "Get all blog")
    @PostMapping("")
    public ResponseEntity<?> getAllBlogs(@RequestBody FindAllBlogRequest request) {
        var params = modelMapper.map(request, GetAllBlogParams.class);

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC, request.getSortBy()));
        AppResult<Page<BlogDTO>> result = blogService.findAll(params);
        if (result.isSuccess()) {
            Page<BlogDTO> blogDTOPage = result.getSuccess();
            List<GetAllBlogResponse> getAllBlogResponses = blogDTOPage.getContent()
                    .stream()
                    .map(blogDTO -> modelMapper.map(blogDTO, GetAllBlogResponse.class))
                    .collect(Collectors.toList());

            Page<GetAllBlogResponse> responsePage = new PageImpl<>(
                    getAllBlogResponses,
                    pageable,
                    blogDTOPage.getTotalElements());

            return ResponseEntity.ok().body(responsePage);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
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
        Blog blog = blogService.findOneBlog(id);

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
    public ResponseEntity<?> create(@Valid @ModelAttribute CreateBlogReq req, HttpServletRequest servletRequest, BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            ApiError apiError = new ApiError(errors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        try {
            var user = iJwtService.getUserByHeaderToken(servletRequest.getHeader("Authorization"));


            Blog blog = new Blog();
            BlogCategory category = blogCategoryService.findBlogCategory(req.getBlogCategoryId());

            blog.setUser(user);
            blog.setTitle(req.getTitle());
            blog.setBlogCategory(category);
            blog.setBlogText(req.getBlogText());

            if (req.getPhoto() != null && !req.getPhoto().isEmpty()) {
                String fileName = uploadPhotoService.uploadPhoto(req.getPhoto());
                blog.setPhoto(fileName);
            }

            Blog createdBlog = blogService.create(blog);
            BlogDTO createdBlogDTO = modelMapper.map(createdBlog, BlogDTO.class);

            return new ResponseEntity<>(createdBlogDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiError apiError = new ApiError("Error when creating blog");
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Edit blog")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @ModelAttribute CreateBlogReq req, BindingResult rs) {
        if (rs.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            rs.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            ApiError apiError = new ApiError(errors);
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = uploadPhotoService.uploadPhoto(req.getPhoto());

            Blog blog = blogService.findOneBlog(id);
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

            Blog updatedBlog = blogService.update(id, blog);

            BlogDTO updatedBlogDTO = modelMapper.map(updatedBlog, BlogDTO.class);

            return new ResponseEntity<>(updatedBlogDTO, HttpStatus.CREATED);

        } catch (Exception e) {
            ApiError apiError = new ApiError("Error when updating blog");
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



    @Operation(summary = "Delete blog")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        return blogService.delete(id);
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody FilterBlogRequest request) {
        var params = modelMapper.map(request, BlogFilterParam.class);

        AppResult<Page<BlogDTO>> result = blogService.filter(params);
        if (result.isSuccess()) {
            Page<BlogDTO> blogDTOPage = result.getSuccess();
            List<FilterBlogResponse> filterBlogResponses = blogDTOPage.getContent()
                    .stream()
                    .map(blogDTO -> modelMapper.map(blogDTO, FilterBlogResponse.class))
                    .collect(Collectors.toList());

            Page<FilterBlogResponse> responsePage = new PageImpl<>(
                    filterBlogResponses,
                    PageRequest.of(request.getPage(), request.getSize()),
                    blogDTOPage.getTotalElements());

            return ResponseEntity.ok().body(responsePage);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }
}
