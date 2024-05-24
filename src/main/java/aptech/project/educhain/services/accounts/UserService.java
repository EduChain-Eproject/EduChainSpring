package aptech.project.educhain.services.accounts;

import aptech.project.educhain.models.accounts.User;
import aptech.project.educhain.repositories.accounts.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public User findUser(Integer id){
        return repository.findById(id).get();
    }
}
