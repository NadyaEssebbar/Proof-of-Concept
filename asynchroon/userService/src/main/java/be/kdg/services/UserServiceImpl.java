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
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by nadya on 31/03/2017.
 */
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    DiscoveryClient discoveryClient;

    public List<User> findUsers()
    {
       return (List)userRepository.findAll();
    }

    public User findUserById(Long id)
    {
        return userRepository.findOne(id);
    }

    public User addUser(User u)
    {
       return userRepository.save(u);
    }


    @Async("threadPoolTaskExecutor")
    public Future<String> callOtherServiceAsync(String service, JSONObject request, String mapping)
    {
        String response="";
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
                response = responseEntity.toString();
            }
        }
         return new AsyncResult<>(response);
    }

    @Override
    public void updateUser(User u)
    {

        userRepository.save(u);
    }
}
