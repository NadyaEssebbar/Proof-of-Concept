package be.kdg.controllers;

import be.kdg.domain.State;
import be.kdg.domain.User;
import be.kdg.services.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by nadya on 30/03/2017.
 */
@RestController
public class UserController
{
    @Autowired
    private UserService userService;


    @RequestMapping(value= "/users", method=RequestMethod.GET)
    public List<User> getUsers()
    {
        return userService.findUsers();
    }

    @RequestMapping("/users/{id}")
    public User getUser(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void addUser( @RequestBody @Valid User u)
    {
      userService.writeToFile(new Timestamp(System.currentTimeMillis()) + ": Userservice enter");

        //create user
        JSONObject request = userService.addUser(u);

        userService.writeToFile( new Timestamp(System.currentTimeMillis()) + " UserService: object created");

        //Ask de AvatarService to create an avatar
        int status = userService.callOtherService("AVATARSERVICE",request, "/avatars");

        //update state user if avatar is created
        if(status == 200)
        {
            u.setState(State.COMPLETED);
            userService.addUser(u);
            userService.writeToFile(new Timestamp(System.currentTimeMillis()) + " UserService: user object completed");

        }

        // send welcome mail
        request = new JSONObject();
        request.put("firstName", u.getFirstName());
        request.put("email", u.getEmail());
        userService.callOtherService("MAILSERVICE",request, "/sendMail");

        userService.writeToFile(new Timestamp(System.currentTimeMillis()) + ": Userservice has finished\n");

    }

    @RequestMapping(value = "/usersAsync", method = RequestMethod.POST)
    public void addUserAsync( @RequestBody @Valid User u)
    {
        userService.writeToFile(new Timestamp(System.currentTimeMillis()) + ": Userservice enter");

        //create user
        JSONObject request = userService.addUser(u);

        userService.writeToFile( new Timestamp(System.currentTimeMillis()) + " UserService: object created");

        //Ask de AvatarService to create an avatar
        ListenableFuture<Integer> future = userService.callOtherServiceAsync("AVATARSERVICE", request, "/avatars");
        future.addCallback(new ListenableFutureCallback<Integer>() {

            public void onSuccess(Integer s)
            {
                //update state user
                if(s == 200)
                {
                    u.setState(State.COMPLETED);
                    userService.updateUser(u);
                    userService.writeToFile(new Timestamp(System.currentTimeMillis()) + " async call UserService: user object completed");
                }
            }
            public void onFailure(Throwable t) {
               //@TODO user verwijderen
            }
        });

        // send welcome mail
        request = new JSONObject();
        request.put("firstName", u.getFirstName());
        request.put("email", u.getEmail());

        userService.callOtherService("MAILSERVICE", request, "/sendMail");

        userService.writeToFile(new Timestamp(System.currentTimeMillis()) + ": Userservice has finished\n");

    }

    @Value("${fo}")
    private String fo;

    @RequestMapping("/fo")
    public String showLuckyWord() {
        return "connection is made to config server: " + fo;
    }



}
