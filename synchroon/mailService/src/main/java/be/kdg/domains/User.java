package be.kdg.domains;

/**
 * Created by nadya on 31/03/2017.
 */
public class User
{
    private String firstName;
    private String email;

    public User() {
    }

    public User(String firstName, String email) {
        this.firstName = firstName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
