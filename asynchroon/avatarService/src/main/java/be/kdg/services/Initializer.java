package be.kdg.services;

import be.kdg.controllers.AvatarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by nadya on 1/02/2017.
 */
@Service
public class Initializer
{
    private AvatarService avatarService;

    @Autowired
    public Initializer(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostConstruct
    public void initialize() throws IOException {
        String imageFile = "C:\\Users\\nadya\\Desktop\\jaar 4\\paper\\Poc_Asynchroon\\avatarService\\src\\main\\resources\\static\\images\\avatar_icon.jpg";

        avatarService.addAvatar(new AvatarDTO(1,"test",imageFile,1L));

        avatarService.getAvatar(1L);

    }

}
