package be.kdg.controllers;

import be.kdg.domain.Avatar;
import be.kdg.services.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by nadya on 31/03/2017.
 */
@RestController
public class AvatarController
{
    @Autowired
    private AvatarService avatarService;


    @RequestMapping("/avatars/{id}")
    public Avatar getAvatar(@PathVariable long id)
    {
        Avatar avatar = null;
        try {
            avatar = avatarService.getAvatar(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return avatar;
    }

    //@TODO use this method with async

   @RequestMapping(value = "/avatars", method = RequestMethod.POST)
    public void addAvatarDTO( @RequestBody @Valid AvatarDTO avatarDTO)
    {
        System.out.println( new Timestamp(System.currentTimeMillis()) + ": Avatarservice enter");
        //Ask the PhraseService to generate a random phrase
            avatarDTO.setPhrase(
                    avatarService.callOtherService("PHRASESERVICE","/getPhrase")
            );
            try {
                avatarService.addAvatar(avatarDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println( new Timestamp(System.currentTimeMillis()) + ": Avatarservice has finished");
    }


        @Value("${fo}")
        private String fo;

        @RequestMapping("/fo")
        public String showLuckyWord () {
            return "connection is made to config server: " + fo;
        }



}
