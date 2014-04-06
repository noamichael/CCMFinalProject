package org.ccm.webcalendar;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.ccm.webcalendar.entity.DatabaseService;
import org.ccm.webcalendar.entity.Event;
import org.ccm.webcalendar.entity.User;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Michael Kucinski/Trevor Florio
 */
@Named
@SessionScoped
public class CalendarController implements Serializable {

    @Inject
    private DatabaseService service;
    @Inject
    private LoginController loginController;
    private User currentUser;
    private List<Event> currentEvents;
    private ScheduleModel eventModel;

    public CalendarController() {

    }

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        currentUser = loginController.getCurrentUser();
        currentEvents = service.findEventByUsername(currentUser.getUsername());
        for (Event e : currentEvents) {
            eventModel.addEvent(e);
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

    /**
     * @return the eventModel
     */
    public ScheduleModel getEventModel() {
        currentUser = loginController.getCurrentUser();
        currentEvents = service.findEventByUsername(currentUser.getUsername());
        for (Event e : currentEvents) {
            eventModel.addEvent(e);
        }
        return eventModel;
    }

    /**
     * @param eventModel the eventModel to set
     */
    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

}
