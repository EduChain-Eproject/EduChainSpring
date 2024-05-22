package aptech.project.educhain.repositories.blogs;

import aptech.project.educhain.models.blogs.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogCommentRepository extends JpaRepository<BlogComment, Integer> {
}
