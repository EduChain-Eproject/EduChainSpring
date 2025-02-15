package aptech.project.educhain.data.entities.blogs;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.accounts.UserInterest;
import aptech.project.educhain.data.entities.courses.Chapter;
import aptech.project.educhain.data.entities.courses.CourseCategory;
import aptech.project.educhain.data.entities.courses.UserCourse;
import aptech.project.educhain.data.entities.others.CourseFeedback;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "tbl_blog_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogCategory extends BaseModel {
    @Column(name = "CategoryName", length = 50)
    @ToString.Exclude
    private String categoryName;

    @OneToMany(mappedBy = "blogCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Blog> blogs;
}
