package aptech.project.educhain.data.repositories.courses;

import aptech.project.educhain.data.entities.courses.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
