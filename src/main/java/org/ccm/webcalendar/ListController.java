package org.ccm.webcalendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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
    private List<Event> currentEvents;
    
    public ListController(){}
    
    @PostConstruct
    public void init(){
        if(loginController.isLoggedIn()){
            setCurrentEvents(loginController.getCurrentUser().getEvents());
        }
        else{
            setCurrentEvents((List<Event>) new ArrayList());
        }
    }

    /**
     * @return the currentEvents
     */
    public List<Event> getCurrentEvents() {
        return currentEvents;
    }

    /**
     * @param currentEvents the currentEvents to set
     */
    public void setCurrentEvents(List<Event> currentEvents) {
        this.currentEvents = currentEvents;
    }
}
