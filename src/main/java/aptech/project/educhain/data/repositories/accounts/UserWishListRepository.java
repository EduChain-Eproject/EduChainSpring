package aptech.project.educhain.data.repositories.accounts;

import aptech.project.educhain.data.entities.accounts.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserWishListRepository extends JpaRepository<UserInterest,Integer> {
    //take list out by student id
    //add course to wishList
    //delete course from wishlist
    @Query("SELECT ui FROM UserInterest ui WHERE ui.user.id = :userId")
    List<UserInterest> findByUserId(@Param("userId") int userId);


}
