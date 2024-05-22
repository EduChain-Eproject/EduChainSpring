package aptech.project.educhain.models.others;

import aptech.project.educhain.models.BaseModel;
import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.models.chats.Message;
import aptech.project.educhain.models.chats.UserChat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_coupons")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon extends BaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", referencedColumnName = "id")
    private User user;

    @Column(name = "Discount", precision = 5, scale = 2)
    private BigDecimal discount;

    @Column(name = "ExpiryDate")
    private Date expiryDate;
}
