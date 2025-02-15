package aptech.project.educhain.data.repositories.payment;

import aptech.project.educhain.data.entities.accounts.User;
import aptech.project.educhain.data.entities.courses.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import aptech.project.educhain.data.entities.payment.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrderByUser(User user);

    List<Order> findOrderByCourse(Course course);
    @Query("SELECT o FROM Order o WHERE o.course.id = :courseId AND (:titleSearch IS NULL OR o.course.title LIKE %:titleSearch%)")
    Page<Order> findOrdersByCourseIdAndTitleSearch(@Param("courseId") Integer courseId, @Param("titleSearch") String titleSearch, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.course = :course")
    Page<Order> findOrderByCourse(@Param("course") Course course, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (:title IS NULL OR o.course.title LIKE %:title%)")
    Page<Order> findOrdersByUserIdAndTitle(@Param("userId") Integer userId, @Param("title") String title, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE :titleSearch IS NULL OR o.course.title LIKE %:titleSearch%")
    Page<Order> findOrdersByTitleSearch(@Param("titleSearch") String titleSearch, Pageable pageable);


}
