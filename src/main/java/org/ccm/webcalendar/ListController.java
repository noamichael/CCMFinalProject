package org.ccm.webcalendar;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import org.ccm.webcalendar.entity.DatabaseService;
import org.ccm.webcalendar.entity.Event;
import org.ccm.webcalendar.entity.User;

/**
 * A session scoped bean to control the list of events displayed in the view.
 *
 * @author Michael Kucinski/Trevor Florio
 */
@Named
@SessionScoped
public class ListController implements Serializable {

    @Inject
    private LoginController loginController;
    @Inject
    private DatabaseService service;
    private transient DataModel<Event> currentEvents;
    private User currentUser;
    private Event selectedEvent;
    private int listType = 0;

    private static final int BY_DATE = 0;
    private static final int BY_PRIORITY = 1;

    public ListController() {
    }

    /**
     * A bean which runs right after construction and sets the current identity.
     */
    @PostConstruct
    public void init() {
        if (loginController.getCurrentUser() == null || !loginController.isLoggedIn()) {
            throw new RuntimeException("The user is not logged in.");
        }
        setCurrentUser(loginController.getCurrentUser());
    }

    public void editEventListener(Event event) {
        setSelectedEvent(event);
    }

    /**
     * Ajax action listener to clear repeated events if needed.
     *
     */
    public void repeatedListener() {
        if (selectedEvent != null && !selectedEvent.isRepeated() && selectedEvent.getRepeatedDays() != null) {
            selectedEvent.getRepeatedDays().clear();
        }

    }

    public void editEvent() {
        if (getCurrentUser().getUsername() != null) {
            service.updateEvent(getSelectedEvent());
            addMessage("Event Updated!");
        } else {
            addMessage("The event could not be updated.");
        }
    }

    /**
     * @return the currentEvents, sorted
     */
    public DataModel<Event> getCurrentEvents() {
        switch (getListType()) {
            case BY_DATE: {
                currentEvents = new ListDataModel(service.findDateSortedEventByUsername(getCurrentUser().getUsername()));
                break;
            }
            case BY_PRIORITY: {
                currentEvents = new ListDataModel(service.findPrioritySortedEventByUsername(getCurrentUser().getUsername()));
                break;
            }
        }
        return currentEvents;
    }

    /**
     * @param currentEvents the currentEvents to set
     */
    public void setCurrentEvents(DataModel<Event> currentEvents) {
        this.currentEvents = currentEvents;
    }

    /**
     * @return the listType
     */
    public int getListType() {
        return listType;
    }

    /**
     * @param listType the listType to set
     */
    public void setListType(int listType) {
        this.listType = listType;
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
     * @return the selectedEvent
     */
    public Event getSelectedEvent() {
        return selectedEvent;
    }

    /**
     * @param selectedEvent the selectedEvent to set
     */
    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }
}
