package be.kdg.dom;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by nadya on 16/04/2017.
 */
@Entity
public class User
{
    @Column(name = "userId", nullable = false)
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private Date birthday;
    @OneToOne(targetEntity = Avatar.class)
    private Avatar avatar;


    public User()
    {
        this.avatar = new Avatar();
    }

    public User(String firstName, String email)
    {
        this();
        this.firstName = firstName;
        this.email = email;
    }

    public User(String firstName, String lastName, String email, Date birthday)
    {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
