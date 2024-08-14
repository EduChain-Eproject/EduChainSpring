package aptech.project.educhain.data.entities.accounts;

import aptech.project.educhain.data.entities.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_reset_password_token")
public class ResetPasswordToken extends BaseModel {
    private Integer resetPasswordToken;
    private Timestamp timeExpire;
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;
}