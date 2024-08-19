package aptech.project.educhain.domain.useCases.blogs.BlogUseCases.BlogFilterUseCase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogFilterParam {
    private int page;
    private int size = 10;
    String keyword;
    String sortStrategy = "DATE_DESC";
    List<Integer> categoryIds;
}
