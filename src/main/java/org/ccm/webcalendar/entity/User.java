package org.ccm.webcalendar.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * An entity which represents a user.
 * @author Trevor Florio
 */
@Entity
@Table(name = "USER_TABLE")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "USERNAME", unique = true)
    @NotNull
    private String username;
    @Column(name = "PASSWORD")
    @NotNull
    private String password;

    public User() {
    }

    public User(String username, long id) {
        this.id = id;
        this.username = username;
    }

    public void updateUser(User user) {

        this.id = user.id;
        this.password = user.password;
        this.username = user.username;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
