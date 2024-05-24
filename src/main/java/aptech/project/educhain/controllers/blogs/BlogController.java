package aptech.project.educhain.controllers.blogs;

import aptech.project.educhain.dto.blogs.BlogRequest;
import aptech.project.educhain.models.blogs.Blog;
import aptech.project.educhain.services.blogs.BlogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
    @Autowired
    BlogService service;

    @GetMapping("")
    public List<Blog> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Blog findOne(@PathVariable Integer id){
        return service.findBlog(id);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody BlogRequest rq, BindingResult rs){
        if(rs.hasErrors()){
            Map<String, String> errors = rs.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Blog blog = new Blog();
        blog.setUser(rq.getUser());
        blog.setTitle(rq.getTitle());
        blog.setBlogCategory(rq.getBlogCategory());
        blog.setBlogText(rq.getBlogText());

        Blog createdBlog = service.create(blog);
        return new ResponseEntity<>(createdBlog, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody BlogRequest rq, BindingResult rs){
        if(rs.hasErrors()){
            Map<String, String> errors = rs.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Blog blog = new Blog();
        blog.setTitle(rq.getTitle());
        blog.setBlogCategory(rq.getBlogCategory());
        blog.setBlogText(rq.getBlogText());

        Blog createdBlog = service.update(id, blog);
        return new ResponseEntity<>(createdBlog, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return service.delete(id);
    }
}
