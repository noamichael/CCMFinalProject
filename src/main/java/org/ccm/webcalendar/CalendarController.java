package org.ccm.webcalendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        updateEvents();
    }

    public void updateEvents() {
        eventModel = new DefaultScheduleModel();
        currentUser = loginController.getCurrentUser();
        currentEvents = service.findEventByUsername(currentUser.getUsername());
        for (Event e : currentEvents) {
            if (e.isRepeated()) {
                for (Event repeated : createRepeatedEvents(e)) {
                    eventModel.addEvent(repeated);
                }
            } else {
                eventModel.addEvent(e);
            }
        }
    }

    public List<Event> createRepeatedEvents(Event event) {
        if (event.getStartDate() == null || event.getEndDate() == null) {
            throw new RuntimeException("Repeated events must have both a start"
                    + "date ");
        }
        List<Event> repeatedDays = new ArrayList();
        Calendar calendar = new GregorianCalendar();
        Date startDate = event.getStartDate();
        calendar.setTime(startDate);
        Date currentDate = event.getStartDate();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        while (!currentDate.equals(event.getEndDate())) {
            if (event.getRepeatedDays().contains(Integer.toString(day))) {
                repeatedDays.add(new Event(event.getTitle(), event.getDescription(), event.getStartDate(), event.getStartDate()));
            }
            calendar.add(Calendar.DATE, 1);
            currentDate = calendar.getTime();
            day = calendar.get(Calendar.DAY_OF_WEEK);
        }
        return repeatedDays;
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
        if (eventModel == null) {
            updateEvents();
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
