package org.ccm.webcalendar;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
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
    private boolean loggedIn;

    public void test() {
        List<Event> list = new ArrayList();
        Event x = new Event();
        x.setStartDate(new Date());
        list.add(x);
        x = new Event();
        x.setStartDate(new Date());
        x = new Event();
        x.setStartDate(new Date());
        list.add(x);
        currentUser.setEvents(list);
        currentUser.sortByDate();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(currentUser.getEvents().toString()));
    }

    public void addEvents() {
        Event test1 = new Event();
        test1.setName("Party");
        test1.setPriority(Event.LOW);
        test1.setStartDate(new Date());
        test1.setDescription("STUFF!");
        Event test2 = new Event();
        test2.setName("School");
        test2.setPriority(Event.HIGH);
        test2.setStartDate(new Date());
        test2.setDescription("STUFF!!");
        Event test3 = new Event();
        test3.setName("Work");
        test3.setPriority(Event.MEDIUM);
        test3.setStartDate(new Date());
        test3.setDescription("STUFF!!!");
        List<Event> list = new ArrayList();
        list.add(test1);
        list.add(test2); 
        list.add(test3);
        currentUser.setEvents(list);
        currentUser.setUsername("John");
        currentUser.setPassword("demo");
        service.addUser(currentUser);
        currentUser = service.findUserByUsername("John");
        String s = "";
        for(Event e: currentUser.getEvents()){
            s += String.format("Name [%s], description [%s], startDate [%s] .", e.getName(), e.getDescription(), e.getStartDate());
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(s));
        
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
