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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

    public void writeToFile(String text) {
        try{

            //Specify the file name and path here
            File file =new File("C:\\Users\\nadya\\Desktop\\logfileSynchronous.txt");


            if(!file.exists()){
                file.createNewFile();
            }

            //Here true is to append the content to file
            FileWriter fw = new FileWriter(file,true);
            //BufferedWriter writer give better performance
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.newLine();
            //Closing BufferedWriter Stream
            bw.close();

        }catch(IOException ioe){
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }
    }
}
