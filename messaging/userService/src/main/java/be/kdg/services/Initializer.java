package be.kdg.services;

import be.kdg.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by nadya on 1/02/2017.
 */
@Service
public class Initializer
{
    private UserService userService;

    @Autowired
    public Initializer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void initialize() {
        userService.addUser(new User("Nadya", "nadya@kdg.be"));
    }

}
