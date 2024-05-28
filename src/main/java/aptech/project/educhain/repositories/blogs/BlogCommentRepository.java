package aptech.project.educhain.repositories.blogs;

import aptech.project.educhain.models.blogs.Blog;
import aptech.project.educhain.models.blogs.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogCommentRepository extends JpaRepository<BlogComment, Integer> {
    public List<BlogComment> findBlogCommentByBlog(Blog blog);
}
