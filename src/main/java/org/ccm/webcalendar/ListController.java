package org.ccm.webcalendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import org.ccm.webcalendar.entity.Event;

/**
 *
 * @author Michael Kucinski/Trevor Florio
 */
@Named
@SessionScoped
public class ListController implements Serializable {
    @Inject
    private LoginController loginController;
    private transient DataModel<Event> currentEvents;
    private List<Event> userEvents;
    
    public ListController(){}
    
    @PostConstruct
    public void init(){
        userEvents = loginController.getCurrentUser().getEvents();
        currentEvents = new ListDataModel();
    }
    public void sortByDate(){
        loginController.getCurrentUser().sortByDate();
    }

    /**
     * @return the userEvents
     */
    public List<Event> updateEvents() {

        return loginController.getCurrentUser().getEvents();
    }

    /**
     * @return the currentEvents
     */
    public DataModel<Event> getCurrentEvents() {
        currentEvents = new ListDataModel(userEvents);
        return currentEvents;
    }

    /**
     * @param currentEvents the currentEvents to set
     */
    public void setCurrentEvents(DataModel<Event> currentEvents) {
        this.currentEvents = currentEvents;
    }

    /**
     * @return the userEvents
     */
    public List<Event> getUserEvents() {
        return userEvents;
    }

    /**
     * @param userEvents the userEvents to set
     */
    public void setUserEvents(List<Event> userEvents) {
        this.userEvents = userEvents;
    }
}
