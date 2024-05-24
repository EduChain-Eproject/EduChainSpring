package aptech.project.educhain.controllers.blogs;

import aptech.project.educhain.request.blogs.BlogCategoryRequest;
import aptech.project.educhain.models.blogs.BlogCategory;
import aptech.project.educhain.services.blogs.BlogCategoryService;
import jakarta.validation.Valid;
import org.springframework.validation.FieldError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/blog_category")
public class BlogCategoryController {
    @Autowired
    BlogCategoryService service;

    @GetMapping("")
    public List<BlogCategory> findAll(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public BlogCategory findOne(@PathVariable Integer id){
        return service.findBlogCategory(id);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody BlogCategoryRequest rq, BindingResult rs){
            if(rs.hasErrors()){
            Map<String, String> errors = rs.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        BlogCategory category = new BlogCategory();
        category.setCategoryName(rq.getCategoryName());

        BlogCategory createdCategory = service.create(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public BlogCategory update(@PathVariable Integer id, @RequestBody BlogCategory category){
        return service.update(id, category);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return service.delete(id);
    }
}
