
package hello.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "sys_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "uname", unique = true, length = 150)
    @NotBlank
    @Length(min = 3, max = 14)
    private String username;

    private String firstname;
    private String lastname;

    @NotBlank
    @Length(min = 5, max = 250)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }


    /**
     * @param firstname The firstname to set.
     * @return User This.
     */
    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }


    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }


    /**
     * @param lastname The lastname to set.
     * @return User This.
     */
    public User setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }


    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }


    /**
     * @param password The password to set.
     * @return User This.
     */
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User() {
    }

    public User(String uname) {
        username = uname;
    }


    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }


    /**
     * @param id The id to set.
     * @return User This.
     */
    public User setId(Long id) {
        this.id = id;
        return this;
    }


    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /** @param username The username to set.
     * @return User This. */
    public User setUsername(String username) {
        this.username = username;
        return this;
    }
}
