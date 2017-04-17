package be.kdg.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by nadya on 31/03/2017.
 */
@Service
public class PhraseServiceImpl implements PhraseService
{
    @Value("${adjective}")
    private String adjectives;

    @Value("${noun}")
    private String nouns;

    @Override
    public String getPhrase()
    {

        return "The " + generateRandomWord(adjectives) + " " + generateRandomWord(nouns);
    }

    private String generateRandomWord(String words)
    {
        String[] wordArray = words.split(",");
        int i = (int)Math.round(Math.random() * (wordArray.length - 1));
        return wordArray[i];
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
