package org.ccm.webcalendar;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.ccm.webcalendar.entity.DatabaseService;
import org.ccm.webcalendar.entity.Event;
import org.ccm.webcalendar.entity.User;

/**
 *
 * @author Michael Kucinski/Trevor Florio
 */
@Named
@ViewScoped
public class ListController implements Serializable {

    @Inject
    private LoginController loginController;
    @Inject
    private DatabaseService service;
    private transient DataModel<Event> currentEvents;
    private User currentUser;

    public ListController() {
    }

    @PostConstruct
    public void init() {
        if (loginController.getCurrentUser() == null || !loginController.isLoggedIn()) {
            throw new RuntimeException("The user is not logged in.");
        }
        currentUser = loginController.getCurrentUser();
    }

    /**
     * @return the currentEvents
     */
    public DataModel<Event> getCurrentEvents() {
        this.currentEvents = new ListDataModel(service.findEventByUsername(currentUser.getUsername()));
        return currentEvents;
    }

    /**
     * @param currentEvents the currentEvents to set
     */
    public void setCurrentEvents(DataModel<Event> currentEvents) {
        this.currentEvents = currentEvents;
    }
}
