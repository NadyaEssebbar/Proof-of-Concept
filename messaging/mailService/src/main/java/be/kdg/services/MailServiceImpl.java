package be.kdg.services;

import be.kdg.domains.User;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by nadya on 31/03/2017.
 */
@Service
public class MailServiceImpl implements MailService
{


    private JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    public void sendWelcomeEmail(User user) throws MailException
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("pockdg2017@gmail.com");
        mailMessage.setSubject("Welcome!");
        mailMessage.setText("Welcome " + user.getFirstName());

        javaMailSender.send(mailMessage);

    }
}
