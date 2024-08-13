package aptech.project.educhain.domain.useCases.blogs.BlogUseCases.FindAllBlogUseCase;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.common.usecase.Usecase;
import aptech.project.educhain.data.entities.blogs.Blog;
import aptech.project.educhain.data.entities.payment.Order;
import aptech.project.educhain.data.repositories.blogs.BlogRepository;
import aptech.project.educhain.domain.dtos.blogs.BlogDTO;
import aptech.project.educhain.domain.dtos.payment.OrderDTO;
import aptech.project.educhain.domain.useCases.payment.order.getAllOrderUseCase.GetAllOrderParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FindAllBlogUseCase implements Usecase<Page<BlogDTO>, GetAllBlogParams> {
    @Autowired
    BlogRepository blogRepository;

    @Autowired
    ModelMapper modelMapper;

    public AppResult<Page<BlogDTO>> execute(GetAllBlogParams params) {
        try {
            Pageable pageable = PageRequest.of(params.getPage(), params.getSize(), Sort.by(params.getSortBy()));
            Page<Blog> blogsPage = blogRepository.findAll(pageable);
            Page<BlogDTO> blogDTOsPage = blogsPage.map(blog -> {
                BlogDTO blogDTO = modelMapper.map(blog, BlogDTO.class);
                return blogDTO;
            });
            return AppResult.successResult(blogDTOsPage);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Failed to get all blogs: " + e.getMessage()));
        }
    }
}
