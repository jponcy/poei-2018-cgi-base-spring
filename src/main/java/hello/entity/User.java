
package hello.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "uname", unique = true, length = 150)
    private String username;

    public User() {
    }

    public User(String uname) {
        username = uname;
    }

    /** @return the id */
    public Long getId() {
        return id;
    }

    /** @param id The id to set.
     * @return User This. */
    public User setId(Long id) {
        this.id = id;
        return this;
    }

    /** @return the username */
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
