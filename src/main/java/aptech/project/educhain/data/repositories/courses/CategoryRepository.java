package aptech.project.educhain.data.repositories.courses;

import aptech.project.educhain.data.entities.chats.Chat;
import aptech.project.educhain.data.entities.courses.Category;
import aptech.project.educhain.data.entities.courses.Course;
import aptech.project.educhain.data.entities.courses.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
