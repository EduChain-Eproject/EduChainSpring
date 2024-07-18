package aptech.project.educhain.data.repositories.blogs;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogCommentRepository extends JpaRepository<BlogComment, Integer> {
    public List<BlogComment> findBlogCommentByBlogAndParentCommentIsNull(Blog blog);

    public List<BlogComment> findBlogCommentByBlog(Blog blog);
}
