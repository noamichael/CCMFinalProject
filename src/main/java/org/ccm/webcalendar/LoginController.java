package org.ccm.webcalendar;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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

    public void test(){
        //currentUser.setEvents();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(""));
    }
    
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
    public String hashPass(String pass) {
        byte[] bytes;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(pass.getBytes());
            bytes = md.digest();
            String sb = "";
            for (byte b : bytes) {
                sb += Integer.toHexString((int) (b & 0xff));
            }
            pass = sb;
        } catch (NoSuchAlgorithmException ex) {

        }
        return pass;
    }
}
