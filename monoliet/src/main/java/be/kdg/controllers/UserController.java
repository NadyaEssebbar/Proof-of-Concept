package be.kdg.controllers;

import be.kdg.dom.User;
import be.kdg.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by nadya on 16/04/2017.
 */
@RestController
public class UserController
{

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping("/users")
    public List<User> getUsers() {
        return userService.findUsers();
    }

    @RequestMapping("/users/{id}")
    public User getUser(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void addUser( @RequestBody @Valid User u)
    {
        userService.addUser(u);
    }


}
