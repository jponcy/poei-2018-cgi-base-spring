
package hello.dto;

import hello.entity.User;

public class UserGetDTO {

    private Long id;

    private String username;

    private String lastname;

    private String firstname;

    public UserGetDTO() {
    }

    public UserGetDTO(User user) {
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
    }

    /** @return the id */
    public Long getId() {
        return id;
    }

    /** @param id The id to set.
     * @return UserGetDTO This. */
    public void setId(Long id) {
        this.id = id;
    }

    /** @return the username */
    public String getUsername() {
        return username;
    }

    /** @param username The username to set.
     * @return UserGetDTO This. */
    public void setUsername(String username) {
        this.username = username;
    }

    /** @return the lastname */
    public String getLastname() {
        return lastname;
    }

    /** @param lastname The lastname to set.
     * @return UserGetDTO This. */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /** @return the firstname */
    public String getFirstname() {
        return firstname;
    }

    /** @param firstname The firstname to set.
     * @return UserGetDTO This. */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
