package be.kdg.services.impl;

import be.kdg.dom.Avatar;
import be.kdg.persistence.AvatarRepository;
import be.kdg.services.api.AvatarService;
import be.kdg.services.api.PhraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nadya on 16/04/2017.
 */
@Service
public class AvatarServiceImpl implements AvatarService
{

    private final AvatarRepository avatarRepository;
     private final PhraseService phraseService;

    @Autowired
    public AvatarServiceImpl(AvatarRepository avatarRepository, PhraseService phraseService) {
        this.avatarRepository = avatarRepository;
        this.phraseService = phraseService;
    }

    @Override
    public Avatar createAvatar(String name)
    {
        String phrase = phraseService.generatePhrase();
        Avatar avatar = new Avatar(name, phrase);
        avatarRepository.save(avatar);
        return avatar;
    }

    @Override
    public List<Avatar> findAvatars() {

    return   (List<Avatar>)avatarRepository.findAll();
    }
}
