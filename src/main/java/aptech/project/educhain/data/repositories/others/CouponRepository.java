package aptech.project.educhain.data.repositories.others;

import aptech.project.educhain.data.entities.chats.Chat;
import aptech.project.educhain.data.entities.others.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Integer>{

}
