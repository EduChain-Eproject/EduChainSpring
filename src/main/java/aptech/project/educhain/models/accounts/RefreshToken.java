package aptech.project.educhain.models.accounts;

import aptech.project.educhain.models.BaseModel;
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
@Table(name = "tbl_refresh_token")
public class RefreshToken extends BaseModel {
    @Column(name = "expireAt")
    private Timestamp expireAt;
    @Transient
    private String accessToken;
    @Column(name = "refreshToken")
    private String refreshToken;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;
}
