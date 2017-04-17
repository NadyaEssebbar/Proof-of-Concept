package be.kdg.services;

import be.kdg.config.ParametersBinding;
import be.kdg.domain.State;
import be.kdg.domain.User;
import be.kdg.domain.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by nadya on 4/04/2017.
 */
@Component
public class Receiver
{
    @Autowired
    UserService userService;
    @Autowired
    RabbitTemplate rabbitTemplate;


    @RabbitListener(queues = ParametersBinding.QUEUE_NAME3)
    public void receiveMessage(Long id) throws IOException
    {

       User u = userService.findUserById(id);

       if(u != null)
       {
           u.setState(State.COMPLETE);
           userService.updateUser(u);
           UserDTO uDTO= new UserDTO(u.getId(),u.getEmail(),u.getFirstName());
           //send message out that user and avatar is created
           rabbitTemplate.convertAndSend(ParametersBinding.EXCHANGE_NAME2, ParametersBinding.ROUTING_KEY2,uDTO);
       }

        System.out.println( new Timestamp(System.currentTimeMillis()) + ": received message from avatar");


    }

}

