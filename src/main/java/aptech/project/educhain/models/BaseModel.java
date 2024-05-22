package aptech.project.educhain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

<<<<<<< Updated upstream
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
=======
@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
>>>>>>> Stashed changes
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
    @Column(name = "created_at")
    private Timestamp createdAt;
}
