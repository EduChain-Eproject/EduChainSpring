package aptech.project.educhain.controllers.blogs;

import aptech.project.educhain.dto.blogs.BlogCategoryDTO;
import aptech.project.educhain.request.blogs.BlogCategoryRequest;
import aptech.project.educhain.models.blogs.BlogCategory;
import aptech.project.educhain.services.blogs.BlogCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.validation.FieldError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "Blog category")
@RestController
@RequestMapping("/api/blog_category")
public class BlogCategoryController {
    @Autowired
    BlogCategoryService service;

    @Autowired
    ModelMapper modelMapper;

    @Operation(summary = "Get all category")
    @GetMapping("")
    public List<BlogCategoryDTO> findAll(){
        List<BlogCategory> categories = service.findAll();
        return categories.stream().map(category -> modelMapper.map(category, BlogCategoryDTO.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Get 1 category")
    @GetMapping("/{id}")
    public BlogCategoryDTO findOne(@PathVariable Integer id){
        return modelMapper.map(service.findBlogCategory(id), BlogCategoryDTO.class);
    }

    @Operation(summary = "Create new category")
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody BlogCategoryRequest rq, BindingResult rs){
            if(rs.hasErrors()){
            Map<String, String> errors = rs.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (service.isExist(rq.getCategoryName())) {
            // Nếu category đã tồn tại, trả về ResponseEntity với thông báo lỗi thích hợp
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Category already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        BlogCategory category = new BlogCategory();
        category.setCategoryName(rq.getCategoryName());

        BlogCategory createdCategory = service.create(category);
        BlogCategoryDTO blogCategoryDTO = modelMapper.map(createdCategory, BlogCategoryDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(blogCategoryDTO);
    }

    @Operation(summary = "Edit category")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody BlogCategory category){
        if (service.isExist(category.getCategoryName())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Category already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        BlogCategory cate = service.update(id, category);
        return ResponseEntity.status(HttpStatus.OK).body(cate);
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return service.delete(id);
    }
}
