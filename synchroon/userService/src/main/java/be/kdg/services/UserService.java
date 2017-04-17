package be.kdg.services;

import be.kdg.domain.User;
import org.json.JSONObject;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by nadya on 31/03/2017.
 */
public interface UserService
{
    List<User> findUsers();
    User findUserById(Long id);
    JSONObject addUser(User u);
    int callOtherService(String service, JSONObject request, String mapping);
    void writeToFile(String text);
    ListenableFuture<Integer> callOtherServiceAsync(String service, JSONObject request, String mapping);

    void updateUser(User u);
}
