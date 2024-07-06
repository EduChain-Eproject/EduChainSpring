package aptech.project.educhain.data.entities.accounts;

import aptech.project.educhain.data.entities.BaseModel;
import aptech.project.educhain.data.entities.courses.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tbl_user_interests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInterest extends BaseModel{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;
}
