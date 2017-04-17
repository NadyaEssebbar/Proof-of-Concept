package be.kdg.services;

import be.kdg.controllers.AvatarDTO;
import be.kdg.domain.Avatar;
import be.kdg.repositories.AvatarRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.bson.types.Binary;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by nadya on 31/03/2017.
 */
@Service
public class AvatarServiceImpl implements AvatarService
{

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    DiscoveryClient discoveryClient;


    @Override
    public Avatar getAvatar(Long UserId) throws IOException {
        Avatar a =  avatarRepository.getAvatarOfUser(UserId);

        InputStream in = new ByteArrayInputStream(a.getImage().getData());
        BufferedImage bImageFromConvert = ImageIO.read(in);

        String locationToStoreImage= "C:\\Users\\nadya\\Desktop\\jaar 4\\paper\\Poc_Asynchroon\\avatarService\\src\\main\\resources\\static\\images\\" + a.getName() + "Image.jpg";


        ImageIO.write(bImageFromConvert, "jpg",new File(locationToStoreImage));

        return a;
    }

    @Override
    public void addAvatar(AvatarDTO avatarDTO) throws IOException
    {

        byte[] imageInByte;
        BufferedImage originalImage = ImageIO.read( new File(avatarDTO.getImageFile()));

        // convert BufferedImage to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, "jpg", baos);
        baos.flush();
        imageInByte = baos.toByteArray();
        baos.close();

        Binary data = new Binary(imageInByte);

        Avatar a = new Avatar(avatarDTO.getId(), avatarDTO.getName(),data, avatarDTO.getUserId(), avatarDTO.getPhrase());
        avatarRepository.save(a);


    }

    @Override
    // @TODO turn this only on when testing hystrix
    // @HystrixCommand(fallbackMethod="getFallbackPhrase")
    public String callOtherService(String service, String mapping)
    {
        String responseEntity=null;
        List<ServiceInstance> list = discoveryClient.getInstances(service);
        if (list != null && list.size() > 0 )
        {
            URI uri = list.get(0).getUri();
            if (uri !=null )
            {

                // send request and parse result
                RestTemplate restTemplate = new RestTemplate();
                responseEntity = restTemplate.getForObject(uri + mapping, String.class);
            }

        }

        return responseEntity;
    }




    private String getFallbackPhrase(String service, String mapping)
    {
        return "This is a fallback phrase!";
    }


}
