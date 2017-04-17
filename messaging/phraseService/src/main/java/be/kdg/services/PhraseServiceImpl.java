package be.kdg.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

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

}
