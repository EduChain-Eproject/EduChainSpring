package aptech.project.educhain.models.blogs;

import aptech.project.educhain.models.BaseModel;
import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.models.accounts.UserInterest;
import aptech.project.educhain.models.courses.Chapter;
import aptech.project.educhain.models.courses.CourseCategory;
import aptech.project.educhain.models.courses.UserCourse;
import aptech.project.educhain.models.others.CourseFeedback;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tbl_blog_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogCategory extends BaseModel {
    @Column(name = "category_name", length = 50)
    private String categoryName;

    @OneToMany(mappedBy = "blogCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Blog> blogs;
}
