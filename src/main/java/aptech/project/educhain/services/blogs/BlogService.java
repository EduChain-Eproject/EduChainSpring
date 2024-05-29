package aptech.project.educhain.services.blogs;

import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.models.blogs.Blog;
import aptech.project.educhain.models.blogs.UserBlogVote;
import aptech.project.educhain.repositories.blogs.BlogRepository;
import aptech.project.educhain.repositories.blogs.UserBlogVoteRepository;
import aptech.project.educhain.services.auth.AuthService;
import aptech.project.educhain.services.blogs.IBlogService.BlogSorting.*;
import aptech.project.educhain.services.blogs.IBlogService.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BlogService implements IBlogService {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogCategoryService categoryService;

    @Autowired
    UserBlogVoteRepository voteRepository;

    @Autowired
    AuthService authService;

    public Blog findBlog(Integer id){
        return blogRepository.findById(id).get();
    }

    @Override
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

    @Override
    public Blog create(Blog newBlog) {
        try {
            return blogRepository.save(newBlog);
        }catch(Exception ex){
            ex.getMessage();
        }
        return null;
    }

    @Override
    public Blog update(Integer id, Blog eBlog) {
        try {
            Blog blog = findBlog(id);
            if(blog != null){
                blog.setId(id);
                blog.setTitle(eBlog.getTitle());
                blog.setBlogText(eBlog.getBlogText());
                blog.setBlogCategory(eBlog.getBlogCategory());
                blogRepository.saveAndFlush(blog);
                return blog;
            }
            return null;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        Blog blog = findBlog(id);
        if(blog != null){
            blogRepository.delete(blog);
            return true;
        }
        return false;
    }

    public List<Blog> findByCategory(List<Blog> blogs, Integer id){
        return blogs.stream().filter(
                blog -> blog
                        .getBlogCategory().getId().equals(id))
                        .collect(Collectors.toList());
    }

    @Override
    public List<Blog> search(List<Blog> blogs, String keyword){
        return blogs.stream()
                .filter(blog -> blog.getTitle().contains(keyword) || blog.getBlogText().contains(keyword))
                .collect(Collectors.toList());
    }

    @Override
    public List<Blog> sorting(List<Blog> blogs, SortStrategy sortStrategy) {
        SortContext sortContext = new SortContext(sortStrategy);
        return sortContext.executeSort(blogs);
    }
    public SortStrategy getSortStrategy(String sortStrategy) {
        if (sortStrategy == null) {
            return new DescendingTimeSort();
        }
        switch (sortStrategy){
            case "ascTitle":
                return new AscendingNameSort();
            case "descTitle":
                return new DescendingNameSort();

            case "ascTime":
                return new AscendingTimeSort();
            case "descTime":
                return new DescendingTimeSort();
            default:
                return new DescendingTimeSort();
        }
    }

    public Blog vote(Integer userId, Integer blogId, int vote){
        User user = authService.findUserById(userId);
        Blog blog = findBlog(blogId);
        UserBlogVote userBlogVote = voteRepository.findUserBlogVoteByUserAndAndBlog(user, blog);
        if (userBlogVote != null){
            if(vote == 0){
                voteRepository.delete(userBlogVote);
            } else {
                userBlogVote.setVote(vote);
                voteRepository.save(userBlogVote);
            }
        } else if (vote != 0) {
            userBlogVote = new UserBlogVote();
            userBlogVote.setUser(user);
            userBlogVote.setBlog(blog);
            userBlogVote.setVote(vote);
            voteRepository.save(userBlogVote);
        }

        blog.setVoteUp(voteRepository.countByBlogAndVote(blog, 1));
        blog.setVoteDown(voteRepository.countByBlogAndVote(blog, -1));
        return blogRepository.save(blog);
    }


    public String uploadPhoto(String uploadDir, MultipartFile photo) throws IOException {
        Path path = Paths.get(uploadDir);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        String fileName = photo.getOriginalFilename() ;
        if (fileName == null) {
            throw new IllegalArgumentException("File name cannot be null");
        }
        Path filePath = path.resolve(fileName);

        Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }


    public Map<String, String> validateFields(String title, Integer userId, Integer blogCategoryId, String blogText) {
        Map<String, String> errors = new HashMap<>();
        if (title == null || title.isEmpty()) {
            errors.put("title", "Title is required");
        }
        if (userId == null) {
            errors.put("userId", "User ID is required");
        }
        if (blogCategoryId == null) {
            errors.put("blogCategoryId", "Blog Category ID is required");
        }
        if (blogText == null || blogText.isEmpty()) {
            errors.put("blogText", "Blog Text is required");
        }
        return errors;
    }

    public Map<String, String> validateFieldsUpdate(String title, Integer blogCategoryId, String blogText) {
        Map<String, String> errors = new HashMap<>();
        if (title == null || title.isEmpty()) {
            errors.put("title", "Title is required");
        }
        if (blogCategoryId == null) {
            errors.put("blogCategoryId", "Blog Category ID is required");
        }
        if (blogText == null || blogText.isEmpty()) {
            errors.put("blogText", "Blog Text is required");
        }
        return errors;
    }
}

