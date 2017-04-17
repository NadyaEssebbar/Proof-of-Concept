package be.kdg.controllers;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;

/**
 * Created by nadya on 31/03/2017.
 */
public class AvatarDTO
{
    private Integer id;
    private String name;
    private String imageFile;
    private Long userId;
    private String phrase;

    public AvatarDTO() {
    }


    public AvatarDTO(Integer id, String name, String imageFile, Long userId) {
        this.id = id;
        this.name = name;
        this.imageFile = imageFile;
        this.userId = userId;
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

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
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
