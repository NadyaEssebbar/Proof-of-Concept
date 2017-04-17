package be.kdg.dom;

import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

/**
 * Created by nadya on 16/04/2017.
 */
@Entity
public class Avatar
{
    @Column(name = "avatarId", nullable = false)
    @Id
    @GeneratedValue
    private Integer avatarId;
    private String name;
    private String phrase;

    public Avatar() {
    }

    public Avatar(String name, String phrase) {
        this.name = name;
        this.phrase = phrase;
    }

    public Integer getId() {
        return avatarId;
    }

    public void setId(Integer avatarId) {
        this.avatarId = avatarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
}
