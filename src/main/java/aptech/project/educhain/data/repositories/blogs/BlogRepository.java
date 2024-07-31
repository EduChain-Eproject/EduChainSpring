package aptech.project.educhain.data.repositories.blogs;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.blogs.BlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    public List<Blog> findBlogByTitleContaining(String keyword);

    @Query("SELECT COUNT(v) FROM UserBlogVote v WHERE v.blog.id = :blogId AND v.vote = 1")
    int countVoteUpByBlogId(@Param("blogId") Integer blogId);

}
