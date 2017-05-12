package be.kdg.services.impl;


import be.kdg.dom.Avatar;
import be.kdg.dom.User;
import be.kdg.persistence.AvatarRepository;
import be.kdg.persistence.UserRepository;
import be.kdg.services.api.AvatarService;
import be.kdg.services.api.MailService;
import be.kdg.services.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by nadya on 31/03/2017.
 */
@Service

public class UserServiceImpl implements UserService
{
   private final UserRepository userRepository;
   private final AvatarService avatarService;
   private final MailService mailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AvatarService avatarService, MailService mailService) {
        this.userRepository = userRepository;
        this.avatarService = avatarService;
        this.mailService = mailService;
    }


    public List<User> findUsers()
    {
       return (List<User>)userRepository.findAll();
    }

    public User findUserById(Long id)
    {
        return userRepository.findOne(id);
    }

    @Transactional
    public void addUser(User u)
    {
        // creëer een avatar en sla deze op
        Avatar avatar = avatarService.createAvatar(u.getFirstName() + "_Avatar");
        System.out.println("Avatar is created");


        //testen om na te kijken of avatar al wordt aangemaakt als user nog niet is aangemaakt
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        u.setAvatar(avatar);
        //sla de user op in de database
        userRepository.save(u);


        //pas als de transactie gecommit is stuur dan een verwelkomingsmail
        TransactionSynchronizationManager.registerSynchronization
                (new TransactionSynchronizationAdapter() {
                    @Override
                    public void afterCommit() {
                        //stuur een mail ter verwelkoming
                        mailService.sendMail(u);
                    }
                });

    }

}
