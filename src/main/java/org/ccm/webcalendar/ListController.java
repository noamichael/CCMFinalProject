package org.ccm.webcalendar;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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
@SessionScoped
public class ListController implements Serializable {

    @Inject
    private LoginController loginController;
    @Inject
    private DatabaseService service;
    private transient DataModel<Event> currentEvents;
    private User currentUser;
    private int listType = 0;

    private static final int BY_DATE = 0;
    private static final int BY_PRIORITY = 1;

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
        switch (getListType()) {
            case BY_DATE: {
                currentEvents = new ListDataModel(service.findDateSortedEventByUsername(currentUser.getUsername()));
                break;
            }
            case BY_PRIORITY: {
                currentEvents = new ListDataModel(service.findPrioritySortedEventByUsername(currentUser.getUsername()));
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
}
