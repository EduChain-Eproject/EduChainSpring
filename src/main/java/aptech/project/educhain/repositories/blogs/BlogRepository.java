package aptech.project.educhain.repositories.blogs;

import aptech.project.educhain.models.blogs.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    public List<Blog> findBlogByTitleContaining(String keyword);
}
