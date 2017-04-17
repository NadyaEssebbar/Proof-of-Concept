package be.kdg.services;

import be.kdg.controllers.AvatarDTO;
import be.kdg.domain.Avatar;
import be.kdg.repositories.AvatarRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.util.List;

/**
 * Created by nadya on 31/03/2017.
 */
@Service
public class AvatarServiceImpl implements AvatarService
{

    private final AvatarRepository avatarRepository;

   private final  DiscoveryClient discoveryClient;

    @Autowired
    public AvatarServiceImpl(AvatarRepository avatarRepository, DiscoveryClient discoveryClient) {
        this.avatarRepository = avatarRepository;
        this.discoveryClient = discoveryClient;
    }


    @Override
    public Avatar getAvatar(Long UserId) throws IOException {
        Avatar a =  avatarRepository.getAvatarOfUser(UserId);

        InputStream in = new ByteArrayInputStream(a.getImage().getData());
        BufferedImage bImageFromConvert = ImageIO.read(in);

        String locationToStoreImage= "C:\\Users\\nadya\\Desktop\\jaar 4\\paper\\Proof of Concept\\synchroon\\avatarService\\src\\main\\resources\\static\\images\\" + a.getName() + "Image.jpg";


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
    //@HystrixCommand(fallbackMethod="getFallbackPhrase")
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

    public void writeToFile(String text) {
        try{

            //Specify the file name and path here
            File file =new File("C:\\Users\\nadya\\Desktop\\logfileSynchronous.txt");


            if(!file.exists()){
                file.createNewFile();
            }

            //Here true is to append the content to file
            FileWriter fw = new FileWriter(file,true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.newLine();
            //Closing BufferedWriter Stream
            bw.close();

        }catch(IOException ioe){
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }
    }

    @Override
    public void dropCollection() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase db = mongoClient.getDatabase("avatarService_RequestResponse");
        MongoCollection col = db.getCollection("avatars");

        if (col.count() > 0) {
            db.drop();
        }
    }


}
