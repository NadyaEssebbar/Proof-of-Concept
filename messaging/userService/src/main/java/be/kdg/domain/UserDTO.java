package be.kdg.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by nadya on 3/04/2017.
 */
public class UserDTO
{
    @JsonProperty("id")
    private Long id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("firstName")
    private String firstName;

    public UserDTO() {
    }

    public UserDTO(Long id, String email, String firstName) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
