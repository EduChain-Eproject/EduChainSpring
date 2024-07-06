package aptech.project.educhain.endpoint.responses.wishlist_response;

import aptech.project.educhain.data.entities.courses.Category;
import lombok.Data;

import java.util.List;
@Data
public class WishListResponse {
    private String description;
    private String title;
    private Double price;
    private String teacherName;
    private List<Category> categoryList;
}
