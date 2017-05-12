package be.kdg.controllers;

import be.kdg.config.ParametersBinding;
import be.kdg.domain.State;
import be.kdg.domain.User;
import be.kdg.domain.UserDTO;
import be.kdg.services.UserService;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadya on 30/03/2017.
 */
@RestController
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private RabbitTemplate rabbitTemplate;




    @RequestMapping("/users")
    public List<User> getUsers() {
        return userService.findUsers();
    }

    @RequestMapping("/users/{id}")
    public User getUser(@PathVariable long id) {
        return userService.findUserById(id);
    }


    //@TODO use whith request/async response
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void addUser( @RequestBody @Valid User u)
    {

        System.out.println( new Timestamp(System.currentTimeMillis()) + ": Userservice enter");

            //Ask de AvatarService to create an avatar
            JSONObject request = new JSONObject();
            request.put("id", 2);
            request.put("name", "avatar1");
            request.put("imageFile","C:\\Users\\nadya\\Desktop\\jaar 4\\paper\\Poc_Synchroon\\avatarService\\src\\main\\resources\\static\\images\\avatar_icon.jpg");
            request.put("userId", u.getId());

            userService.callOtherServiceAsync("AVATARSERVICE",request, "/avatars");


            userService.addUser(u);

                // send welcome mail
                request = new JSONObject();
                request.put("firstName", u.getFirstName());
                request.put("email", u.getEmail());
                userService.callOtherServiceAsync("MAILSERVICE",request, "/sendMail");

        System.out.println( new Timestamp(System.currentTimeMillis()) + ": Userservice has finished");

    }


    //messaging

    @RequestMapping(value="/messages/addUser", method = RequestMethod.POST)
    public void addUserEvent(@ RequestBody @Valid User u)
    {
        System.out.println( new Timestamp(System.currentTimeMillis()) + ": Userservice enter");

        //create new user
        u.setState(State.NEW);
        u = userService.addUser(u);
        UserDTO uDTO= new UserDTO(u.getId(),u.getEmail(),u.getFirstName());

        //send message out that user is created
        rabbitTemplate.convertAndSend(ParametersBinding.EXCHANGE_NAME, ParametersBinding.ROUTING_KEY,uDTO);

        System.out.println( new Timestamp(System.currentTimeMillis()) + ": Userservice has finished");
    }

    @Value("${fo}")
    private String fo;

    @RequestMapping("/fo")
    public String showLuckyWord() {
        return "connection is made to config server: " + fo;
    }



}
