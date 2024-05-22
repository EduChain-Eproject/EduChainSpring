package aptech.project.educhain.repositories.blogs;

import aptech.project.educhain.models.blogs.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
}
