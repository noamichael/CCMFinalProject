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
//                for (Event repeated : createRepeatedEvents(e)) {
//                    //eventModel.addEvent(repeated);
//                }
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
        Calendar startCal = new GregorianCalendar();
        Calendar endCal = new GregorianCalendar();
        Calendar finalCal = new GregorianCalendar();

        Date startDate = event.getStartDate();
        Date endDate = event.getEndDate();

        startCal.setTime(startDate);
        endCal.setTime(endDate);

        int startTime = startCal.get(Calendar.HOUR);
        int endTime = endCal.get(Calendar.HOUR);

        Date currentDate = event.getStartDate();
        Date currentEndDate = event.getStartDate();
        int day = startCal.get(Calendar.DAY_OF_WEEK);
        while (!currentDate.equals(event.getEndDate())) {
            if (event.getRepeatedDays().contains(dayIntToString(day))) {
                finalCal.setTime(currentDate);
                finalCal.set(Calendar.HOUR_OF_DAY, startTime);
                endCal.setTime(currentEndDate);
                endCal.set(Calendar.HOUR_OF_DAY, endTime);
                repeatedDays.add(new Event(event.getTitle(), event.getDescription(), finalCal.getTime(), endCal.getTime()));
            }
            startCal.add(Calendar.DATE, 1);
            currentDate = startCal.getTime();
            currentEndDate = endCal.getTime();
            day = startCal.get(Calendar.DAY_OF_WEEK);
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

    public String dayIntToString(int day) {
        String dayString = "";
        switch (day) {
            case 1:
                dayString = "SUN";
                break;
            case 2:
                dayString = "MON";
                break;
            case 3:
                dayString = "TUE";
                break;
            case 4:
                dayString = "WED";
                break;
            case 5:
                dayString = "THU";
                break;
            case 6:
                dayString = "FRI";
                break;
            case 7:
                dayString = "SAT";
                break;
            default:
                break;
        }
        return dayString;
    }

}
