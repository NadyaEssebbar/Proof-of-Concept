package be.kdg.services.api;

import be.kdg.dom.User;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;

/**
 * Created by nadya on 31/03/2017.
 */
public interface UserService
{
    List<User> findUsers();
    User findUserById(Long id);
    void addUser(User u);
}
