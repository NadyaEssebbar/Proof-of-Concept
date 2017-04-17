package be.kdg;

import be.kdg.services.PhraseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * Created by nadya on 31/03/2017.
 */
@RestController
public class PhraseController
{
    @Autowired
    PhraseService phraseService;


    @RequestMapping("/getPhrase")
    public String welcome()
    {
        phraseService.writeToFile( new Timestamp(System.currentTimeMillis()) + ": Phraseservice enter");

        //to test the blocking of a service
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        phraseService.writeToFile(  new Timestamp(System.currentTimeMillis()) + ": Phraseservice has finished");
        return phraseService.getPhrase();
    }

}
