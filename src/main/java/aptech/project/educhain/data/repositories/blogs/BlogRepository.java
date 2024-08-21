package aptech.project.educhain.data.repositories.blogs;

import aptech.project.educhain.data.entities.blogs.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    @Query("SELECT b FROM Blog b JOIN b.blogCategory cat WHERE "
            + "(LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%')) OR "
            + "LOWER(b.blogText) LIKE LOWER(CONCAT('%', :search, '%'))) "
            +  "AND (:categoryIds IS NULL OR cat.id IN :categoryIds) "
            + "ORDER BY CASE WHEN :sortStrategy = 'DATE_ASC' THEN b.createdAt END ASC, "
            + "CASE WHEN :sortStrategy = 'DATE_DESC' THEN b.createdAt END DESC, "
            + "CASE WHEN :sortStrategy = 'MOST_COMMENT' THEN SIZE(b.blogComments) END DESC, "
            + "CASE WHEN :sortStrategy = 'MOST_LIKE' THEN b.voteUp END DESC")
    Page<Blog> filter(
            @Param("categoryIds") List<Integer> categoryIds,
            @Param("search") String keyword,
            @Param("sortStrategy") String sortStrategy,
            Pageable pageable);


    @Query("SELECT COUNT(v) FROM UserBlogVote v WHERE v.blog.id = :blogId AND v.vote = 1")
    int countVoteUpByBlogId(@Param("blogId") Integer blogId);

    @Query("SELECT b FROM Blog b LEFT JOIN FETCH b.blogCategory")
    Page<Blog> findAllWithCategory(Pageable pageable);


}
