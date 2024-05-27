package aptech.project.educhain.controllers.blogs;

import aptech.project.educhain.dto.blogs.BlogDTO;
import aptech.project.educhain.request.blogs.BlogRequest;
import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.models.blogs.Blog;
import aptech.project.educhain.models.blogs.BlogCategory;
import aptech.project.educhain.request.blogs.SortBlogRequest;
import aptech.project.educhain.services.accounts.UserService;
import aptech.project.educhain.services.blogs.BlogCategoryService;
import aptech.project.educhain.services.blogs.BlogService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
    @Autowired
    BlogService service;

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BlogCategoryService blogCategoryService;

    @GetMapping("")
    public List<BlogDTO> findAll(){
        List<Blog> blogs = service.findAll();

        return blogs.stream().map(blog -> modelMapper.map(blog, BlogDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BlogDTO findOne(@PathVariable Integer id){
        Blog blog =  service.findBlog(id);

        return modelMapper.map(blog, BlogDTO.class);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody BlogRequest rq, BindingResult rs){
        if(rs.hasErrors()){
            Map<String, String> errors = rs.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Blog blog = new Blog();
        User user = userService.findUser(rq.getUserId());
        BlogCategory category = blogCategoryService.findBlogCategory(rq.getBlogCategoryId());

        blog.setUser(user);
        blog.setTitle(rq.getTitle());
        blog.setBlogCategory(category);
        blog.setBlogText(rq.getBlogText());

        Blog createdBlog = service.create(blog);

        BlogDTO createdBlogDTO = modelMapper.map(createdBlog, BlogDTO.class);

        return new ResponseEntity<>(createdBlogDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody BlogRequest rq, BindingResult rs){
        if(rs.hasErrors()){
            Map<String, String> errors = rs.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Blog blog = service.findBlog(id);
        BlogCategory category = blogCategoryService.findBlogCategory(rq.getBlogCategoryId());

        blog.setTitle(rq.getTitle());
        blog.setBlogCategory(category);
        blog.setBlogText(rq.getBlogText());

        Blog updatedBlog = service.update(id, blog);

        BlogDTO updatedBlogDTO = modelMapper.map(updatedBlog, BlogDTO.class);

        return new ResponseEntity<>(updatedBlogDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return service.delete(id);
    }

    @GetMapping("")
    public List<Blog> sortAndSearch(@RequestBody SortBlogRequest rq){
        List<Blog> blogs = service.findAll();
        blogs = service.search(rq.getKeyword());
        if(blogs != null){
            blogs =  service.sorting(blogs, service.getSortStrategy(rq.getSortStrategy()));
            if (blogs != null){
                return blogs;
            }
        }
        return Collections.emptyList();
    }
}
