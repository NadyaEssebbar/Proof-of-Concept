package be.kdg.services.impl;

import be.kdg.dom.User;
import be.kdg.services.api.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by nadya on 16/04/2017.
 */
@Service
public class MailServiceImpl implements MailService
{
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(User user) throws MailException
    {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("pockdg2017@gmail.com");
        mailMessage.setSubject("Welcome!");
        mailMessage.setText("Welcome " + user.getFirstName());

        javaMailSender.send(mailMessage);

    }
}
