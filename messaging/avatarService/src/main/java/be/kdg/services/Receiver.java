package be.kdg.services;

import be.kdg.config.ParametersBinding;
import be.kdg.controllers.AvatarDTO;
import be.kdg.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by nadya on 1/04/2017.
 */
@Component
public class Receiver
{
    @Autowired
    AvatarService avatarService;
    @Autowired
    RabbitTemplate rabbitTemplate;


    @RabbitListener(queues = ParametersBinding.QUEUE_NAME)
    public void receiveMessage(byte[] user) throws IOException
    {

        ObjectMapper mapper = new ObjectMapper();
        User u = mapper.readValue(new String(user), User.class);

        System.out.println( new Timestamp(System.currentTimeMillis()) + ": received message from userService");
            //create Avatar
            AvatarDTO  avatarDTO=
                    new AvatarDTO(u.getId().intValue(),"avatar1","C:\\Users\\nadya\\Desktop\\jaar 4\\paper\\Poc_Asynchroon\\avatarService\\src\\main\\resources\\static\\images\\avatar_icon.jpg",u.getId());


            //Ask the PhraseService to generate a random phrase
            avatarDTO.setPhrase(
                    avatarService.callOtherService("PHRASESERVICE","/getPhrase")
            );
            try {
                avatarService.addAvatar(avatarDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //send message out that user is created
            rabbitTemplate.convertAndSend(ParametersBinding.EXCHANGE_NAME2, ParametersBinding.ROUTING_KEY2,u.getId());

            //@TODO send message avatar is gecreerd
            System.out.println( new Timestamp(System.currentTimeMillis()) + ": send message to UserService");


    }

}
