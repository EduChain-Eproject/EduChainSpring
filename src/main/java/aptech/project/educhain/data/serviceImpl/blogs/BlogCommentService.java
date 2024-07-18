package aptech.project.educhain.data.serviceImpl.blogs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.domain.dtos.blogs.BlogCommentDTO;
import aptech.project.educhain.domain.useCases.blogs.BlogCommentUseCases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aptech.project.educhain.data.entities.blogs.BlogComment;
import aptech.project.educhain.data.repositories.blogs.BlogCommentRepository;
import aptech.project.educhain.domain.services.blogs.IBlogCommentService;

@Service
public class BlogCommentService implements IBlogCommentService {
    @Autowired
    FindCommentUseCase findCommentUseCase;

    @Autowired
    FindCommentByBlogUseCase findCommentByBlogUseCase;

    @Autowired
    AddCommentUseCase addCommentUseCase;

    @Autowired
    EditCommentUseCase editCommentUseCase;

    @Autowired
    DeleteCommentUseCase deleteCommentUseCase;

    @Autowired
    MapChildCommentUseCase mapChildCommentUseCase;

    @Override
    public BlogComment findComment(Integer id) {
        return findCommentUseCase.execute(id);
    }

    @Override
    public List<BlogComment> findCommentByBlog(Integer id) {
        return findCommentByBlogUseCase.execute(id);
    }

    @Override
    public BlogComment addComment(BlogComment comment) {
        return addCommentUseCase.execute(comment);
    }

    @Override
    public BlogComment editComment(Integer id, BlogComment comment) {
        return editCommentUseCase.execute(id, comment);
    }

    @Override
    public boolean deleteComment(Integer id) {
        return deleteCommentUseCase.execute(id);
    }

    @Override
    public BlogCommentDTO mapChildCommentService(BlogComment comment, Integer blogId) {
        return mapChildCommentUseCase.execute(comment, blogId);
    }
}
