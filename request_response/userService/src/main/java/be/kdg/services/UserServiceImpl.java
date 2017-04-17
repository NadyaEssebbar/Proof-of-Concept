package be.kdg.services;

import be.kdg.domain.State;
import be.kdg.domain.User;
import be.kdg.repositories.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by nadya on 31/03/2017.
 */
@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;


    private final DiscoveryClient discoveryClient;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, DiscoveryClient discoveryClient) {
        this.userRepository = userRepository;
        this.discoveryClient = discoveryClient;
    }

    public List<User> findUsers()
    {
       return (List)userRepository.findAll();
    }

    public User findUserById(Long id)
    {
        return userRepository.findOne(id);
    }

    public JSONObject addUser(User u)
    {
        u.setState(State.NEW);
        userRepository.save(u);
        JSONObject request = new JSONObject();
        request.put("id", u.getId().intValue());
        request.put("name", "avatar1");
        request.put("imageFile","C:\\Users\\nadya\\Desktop\\jaar 4\\paper\\Poc_Synchroon\\avatarService\\src\\main\\resources\\static\\images\\avatar_icon.jpg");
        request.put("userId", u.getId());

        return request;

    }

    @Override
    public void updateUser(User u) {
        userRepository.save(u);
    }

    public int callOtherService(String service, JSONObject request, String mapping)
    {
        int statuscode=0;
        List<ServiceInstance> list = discoveryClient.getInstances(service);
        if (list != null && list.size() > 0 ) {
            URI uri = list.get(0).getUri();
            if (uri !=null )
            {

                // set headers
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);

                // send request and parse result
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseEntity = restTemplate.exchange(uri + mapping, HttpMethod.POST, entity, String.class);
                statuscode = responseEntity.getStatusCodeValue();
            }
        }

        return statuscode;
    }

    @Async("threadPoolTaskExecutor")
    public ListenableFuture<Integer> callOtherServiceAsync(String service, JSONObject request, String mapping)
    {
        return new AsyncResult<>(callOtherService(service,request,mapping));
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
}
