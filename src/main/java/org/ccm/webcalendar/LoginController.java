package org.ccm.webcalendar;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import org.ccm.webcalendar.entity.DatabaseService;
import org.ccm.webcalendar.entity.User;

/**
 *
 * @author Michael Kucinski
 */
@SessionScoped
public class LoginController implements Serializable {
    @Inject
    private DatabaseService service;
    private User currentUser;
    private boolean loggedIn;

    /**
     * @return the currentUser
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @return the loggedIn
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    @PostConstruct
    public void init(){
        this.currentUser = new User();
    }
}
