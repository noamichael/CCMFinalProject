package org.ccm.webcalendar;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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

    public void register() {
        newUser.setPassword(hashPass(newUser.getPassword()));
        getService().addUser(getNewUser());
        newUser = new User();
        addMessage("Account created! Feel free to login.");
    }

    public void login() {
        this.setLoggedIn(true);
        this.setCurrentUser(getService().findUserByUsername(getCurrentUser().getUsername()));
    }

    public void logout() {
        this.setLoggedIn(false);
        this.setCurrentUser(new User());
    }

    public void addEvent() {
        if (getCurrentUser().getUsername() != null) {
            List<Event> allEvents = getCurrentUser().getEvents();
            
            newEvent.setUserId(currentUser);
            allEvents.add(getNewEvent());
            
            currentUser.setEvents(allEvents);
            service.updateUser(currentUser);
            newEvent = new Event();
            addMessage("Event added!");
        }
        else{
            addMessage("The event could not be added.");
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

    @PostConstruct
    public void init() {
        this.setCurrentUser(new User());
        this.setNewEvent(new Event());
        this.newUser = new User();
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
