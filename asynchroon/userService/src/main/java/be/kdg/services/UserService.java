package be.kdg.services;

import be.kdg.domain.State;
import be.kdg.domain.User;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by nadya on 31/03/2017.
 */
public interface UserService
{
    List<User> findUsers();
    User findUserById(Long id);
    User addUser(User u);
    Future<String> callOtherServiceAsync(String service, JSONObject request, String mapping);

    void updateUser(User u);
}
