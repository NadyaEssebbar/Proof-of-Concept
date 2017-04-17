package be.kdg.services;

import be.kdg.Config.ParametersBinding;
import be.kdg.domains.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by nadya on 3/04/2017.
 */
public class Receiver
{
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    MailService mailService;


    @RabbitListener(queues = ParametersBinding.QUEUE_NAME2)
    public void receiveMessage(byte[] user) throws IOException
    {

        ObjectMapper mapper = new ObjectMapper();
        User u = mapper.readValue(new String(user), User.class);

        mailService.sendWelcomeEmail(u);

        System.out.println( new Timestamp(System.currentTimeMillis()) + ": received message from userService");
    }
}
