package be.kdg.controllers;

import be.kdg.domains.User;
import be.kdg.services.MailService;
import be.kdg.services.MailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Timestamp;

/**
 * Created by nadya on 31/03/2017.
 */
@RestController
public class Mailcontroller
{
    @Autowired
    private MailService mailService;

    Logger logger = LoggerFactory.getLogger(Mailcontroller.class);

    @RequestMapping("/sendMail")
    public void welcome(@RequestBody @Valid User u)
    {
        System.out.println( new Timestamp(System.currentTimeMillis()) + ": Mailservice enter");

        try {
            mailService.sendWelcomeEmail(u);
        }catch(MailException e)
        {
            logger.info("Error sending welcome email: " + e);
        }

        System.out.println( new Timestamp(System.currentTimeMillis()) + ": Mailservice has finished");

    }

    @Value("${fo}")
    private String fo;

    @RequestMapping("/fo")
    public String showLuckyWord() {
        return "connection is made to config server: " + fo;
    }
}
