package org.ccm.webcalendar;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.ccm.webcalendar.entity.DatabaseService;
import org.ccm.webcalendar.entity.Event;
import org.ccm.webcalendar.entity.User;

/**
 *
 * @author Michael Kucinski
 */
@SessionScoped
@Named
public class LoginController implements Serializable {

    @Inject
    private DatabaseService service;
    private User currentUser;
    private User newUser;
    private boolean loggedIn;
    private Event newEvent;

    /**
     * Registers a user after validation.
     */
    public void register() {
        newUser.setPassword(hashPass(newUser.getPassword()));
        getService().addUser(getNewUser());
        newUser = new User();
        addMessage("Account created! Feel free to login.");
    }

    /**
     * Logs in a user after validation.
     */
    public void login() {
        this.setLoggedIn(true);
        this.setCurrentUser(getService().loginByUsername(getCurrentUser().getUsername()));
        if (this.currentUser == null) {
            throw new RuntimeException("NULL USER!");
        }
    }

    /**
     * Logs out a user and invalidates the current session.
     */
    public void logout() {
        this.setLoggedIn(false);
        this.setCurrentUser(new User());
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

    /**
     * Ajax action listener to clear repeated events if needed.
     */
    public void repeatedListener() {
        if (!newEvent.isRepeated() && newEvent.getRepeatedDays() != null) {
            newEvent.getRepeatedDays().clear();
        }
    }

    /**
     * Adds a new event to the persistence context
     */
    public void addEvent() {
        if (getCurrentUser().getUsername() != null) {
            newEvent.setOwner(currentUser);
            service.addEvent(newEvent);
            newEvent = new Event();
            addMessage("Event added!");
        } else {
            addMessage("The event could not be added because the user is not logged in.");
        }

    }

    /**
     * Removes an event from the persistence context.
     *
     * @param event The event to remove.
     */
    public void removeEvent(Event event) {
        if (getCurrentUser().getUsername() != null) {
            service.removeEvent(event);
            addMessage("Event Removed!");
        } else {
            addMessage("The event could not be removed.");
        }
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

    /**
     * A method which runs after construction and initializes class with default
     * values.
     */
    @PostConstruct
    public void init() {
        this.setCurrentUser(new User());
        this.setNewEvent(new Event());
        this.newUser = new User();
    }

    /**
     * Hashes a String with SHA.
     *
     * @param pass the password to be encrypted
     * @return the encrypted password
     */
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

    /**
     * Adds a message to the view.
     *
     * @param message The message to be displayed on the view.
     */
    public void addMessage(String message) {
        FacesMessage msg = new FacesMessage(message);
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * @return the service
     */
    public DatabaseService getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(DatabaseService service) {
        this.service = service;
    }

    /**
     * @return the newUser
     */
    public User getNewUser() {
        return newUser;
    }

    /**
     * @param newUser the newUser to set
     */
    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    /**
     * @return the newEvent
     */
    public Event getNewEvent() {
        return newEvent;
    }

    /**
     * @param newEvent the newEvent to set
     */
    public void setNewEvent(Event newEvent) {
        this.newEvent = newEvent;
    }
}
