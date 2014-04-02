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
    private List<Event> userEvents;
    private DataModel<Event> currentEvents;
    
    public ListController(){}
    
    @PostConstruct
    public void init(){
        currentEvents = new ListDataModel();
        if(loginController.isLoggedIn()){
            setUserEvents(loginController.getCurrentUser().getEvents());
        }
        else{
            setUserEvents((List<Event>) new ArrayList());
        }
    }
    public void sortByDate(){
        loginController.getCurrentUser().sortByDate();
        this.currentEvents.setWrappedData(getUserEvents());
    }

    /**
     * @return the userEvents
     */
    public List<Event> getUserEvents() {

        return loginController.getCurrentUser().getEvents();
    }

    /**
     * @param userEvents the userEvents to set
     */
    public void setUserEvents(List<Event> userEvents) {
        this.userEvents = userEvents;
    }

    /**
     * @return the currentEvents
     */
    public DataModel<Event> getCurrentEvents() {
        currentEvents.setWrappedData(getUserEvents());
        return currentEvents;
    }

    /**
     * @param currentEvents the currentEvents to set
     */
    public void setCurrentEvents(DataModel<Event> currentEvents) {
        this.currentEvents = currentEvents;
    }
}
