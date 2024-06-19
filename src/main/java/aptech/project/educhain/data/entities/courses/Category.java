package aptech.project.educhain.data.entities.courses;

import java.util.List;

import aptech.project.educhain.data.entities.BaseModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseModel {
    @Column(name = "category_name")
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private List<Course> courses;
}