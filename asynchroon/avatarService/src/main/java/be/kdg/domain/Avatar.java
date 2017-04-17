package be.kdg.domain;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;

/**
 * Created by nadya on 31/03/2017.
 */
@Document(collection ="avatars")
public class Avatar
{
    @Id
    private Integer id;
    private String name;
    private Binary image;
    private Long userId;
    private String phrase;

    public Avatar() {
    }

    public Avatar(Integer id, String name, Binary image, Long userId, String phrase)
    {
        this.id = id;
        this.name = name;
        this.image = image;
        this.userId = userId;
        this.phrase = phrase;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
