package be.kdg.services.impl;

import be.kdg.services.api.PhraseService;
import org.springframework.stereotype.Service;

/**
 * Created by nadya on 16/04/2017.
 */
@Service
public class PhraseServiceImpl implements PhraseService
{
    private String [] adjectives = new String[] {"reasonable","leaky","suspicious","ordinary","humongous","aggressive","beautiful","magnificent"};
    private String [] nouns = new String[] {"werewolf","vampire","witch","zombie","human","mamal","dinosaur","superhero"};

    @Override
    public String generatePhrase() {
        return "The " + generateRandomWord(adjectives) + " " + generateRandomWord(nouns);
    }

    private String generateRandomWord(String [] words)
    {
        int i = (int)Math.round(Math.random() * (words.length - 1));
        return words[i];
    }
}
