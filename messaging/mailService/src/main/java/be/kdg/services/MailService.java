package be.kdg.services;

import be.kdg.domains.User;
import org.springframework.mail.MailException;

/**
 * Created by nadya on 31/03/2017.
 */
public interface MailService
{
    void sendWelcomeEmail(User user) throws MailException;
}
