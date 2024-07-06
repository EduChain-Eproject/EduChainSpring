package aptech.project.educhain.data.entities.courses;

import java.util.List;

import aptech.project.educhain.data.entities.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseModel {
    @Column(name = "category_name")
    private String categoryName;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "categories")
    private List<Course> courses;
}