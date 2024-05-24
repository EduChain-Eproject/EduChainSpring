package aptech.project.educhain.repositories.accounts;

import aptech.project.educhain.models.accounts.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
