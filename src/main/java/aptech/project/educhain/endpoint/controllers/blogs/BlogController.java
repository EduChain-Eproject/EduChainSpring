package aptech.project.educhain.endpoint.controllers.blogs;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

        return blogs.stream().map(blog -> modelMapper.map(blog, BlogDTO.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Get 1 blog")
    @GetMapping("/{id}")
    public BlogDTO findOne(@PathVariable Integer id) {
        Blog blog = service.findOneBlog(id);

        return modelMapper.map(blog, BlogDTO.class);
    }

    @Operation(summary = "Add new blog")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@RequestParam("title") String title, @RequestParam("userId") Integer userId,
            @RequestParam("blogCategoryId") Integer blogCategoryId, @RequestParam("blogText") String blogText,
            @RequestParam("photo") MultipartFile photo) {
        Map<String, String> errors = service.validateFields(title, userId, blogCategoryId, blogText);
        if (!errors.isEmpty()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        try {
            String fileName = uploadPhotoService.uploadPhoto(uploadDir, photo);

            Blog blog = new Blog();
            User user = userService.findUserById(userId);
            BlogCategory category = blogCategoryService.findBlogCategory(blogCategoryId);

            blog.setUser(user);
            blog.setTitle(title);
            blog.setBlogCategory(category);
            blog.setBlogText(blogText);
            blog.setPhoto(fileName);

            Blog createdBlog = service.create(blog);

            BlogDTO createdBlogDTO = modelMapper.map(createdBlog, BlogDTO.class);

            return new ResponseEntity<>(createdBlogDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Vote up or vote down")
    @PostMapping("/vote/{id}")
    public BlogDTO vote(
            @RequestParam Integer userId,
            @RequestParam Integer vote,
            @PathVariable Integer id) {
        User user = userService.findUserById(userId);
        Blog blog = service.findOneBlog(id);
        Blog updatedBlog = userBlogVoteService.vote(userId, id, vote);
        return modelMapper.map(updatedBlog, BlogDTO.class);
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
            String fileName = uploadPhotoService.uploadPhoto(uploadDir, photo);

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

    @Operation(summary = "Filter")
    @GetMapping("/filter")
    public List<BlogDTO> filter(@RequestBody FilterBlogRequest rq) {
        List<Blog> blogs = service.findAll();
        if (rq.getCategoryId() != null) {
            blogs = service.findByCategory(blogs, rq.getCategoryId());
        }
        if (rq.getKeyword() != null) {
            blogs = service.search(blogs, rq.getKeyword());
        }
        if (rq.getSortStrategy() != null) {
            blogs = service.sorting(blogs, service.getSortStrategy(rq.getSortStrategy()));
        }
        return blogs.stream().map(blog -> modelMapper.map(blog, BlogDTO.class)).collect(Collectors.toList());
    }
}
