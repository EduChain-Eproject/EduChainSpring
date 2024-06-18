package aptech.project.educhain.data.entities.courses;

import aptech.project.educhain.data.entities.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tbl_course_category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseCategory extends BaseModel {
    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "courseCategory", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Course> courses;
}