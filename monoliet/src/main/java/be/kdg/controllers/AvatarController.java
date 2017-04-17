package be.kdg.controllers;

import be.kdg.dom.Avatar;
import be.kdg.services.api.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by nadya on 16/04/2017.
 */
@RestController
public class AvatarController
{
    private final AvatarService avatarService;

    @Autowired
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }


    @RequestMapping("/avatars")
    public List<Avatar> getAvatars() {
        return avatarService.findAvatars();
    }
}
