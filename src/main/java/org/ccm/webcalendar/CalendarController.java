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
 * A session scoped bean to manage the view's calendar component
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

    /**
     * A method which runs after the bean's construction.
     */
    @PostConstruct
    public void init() {
        updateEvents();
    }

    /**
     * An action listener to update the event model.
     */
    public void updateEvents() {
        eventModel = new DefaultScheduleModel();
        currentUser = loginController.getCurrentUser();
        currentEvents = service.findEventByUsername(currentUser.getUsername());
        for (Event e : currentEvents) {
             eventModel.addEvent(e);//remove if the code below is uncommented
//            if (e.isRepeated()) {
//                for (Event repeated : createRepeatedEvents(e)) {
//                    eventModel.addEvent(repeated);
//                }
//            } else {
//                eventModel.addEvent(e);
//            }
        }
    }

    /**
     * A method to create an event object for every day that that event repeats.
     *
     * @param event the event to be repeated
     * @return the list of all of the events for which it is repeated.
     */
    public List<Event> createRepeatedEvents(Event event) {
        if (event.getStartDate() == null || event.getEndDate() == null) {
            throw new RuntimeException("Repeated events must have both a start"
                    + "date ");
        }
        List<Event> days = new ArrayList();

        //Set the initial dates
        Date startDate = event.getStartDate();
        Date endDate = event.getEndDate();

        //Create the initial calendar objects
        Calendar startCal = new GregorianCalendar();
        Calendar endCal = new GregorianCalendar();
        startCal.setTime(startDate);
        endCal.setTime(endDate);

        long tempTime = startDate.getTime();
        int tempDay = startCal.get(Calendar.DAY_OF_WEEK);

        while (tempTime <= endDate.getTime()) {
            if (event.getRepeatedDays().contains(dayIntToString(tempDay))) {
                Event e = new Event();
                e.updateEvent(event);
                e.setRepeated(false);
                days.add(e);
            }
            tempTime += 86400000;
            if (tempDay != Calendar.SATURDAY) {
                tempDay += 1;
            } else {
                tempDay = Calendar.SUNDAY;
            }
        }

        return days;
    }

    /**
     * finds the distance between a date and the next day it will repeat on and
     * returns the new day.
     *
     * @param currentDate
     * @param day
     * @return
     */
    public Date findNextDay(Date currentDate, String day) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        int oldDay = cal.get(Calendar.DAY_OF_WEEK);
        int nextDay = dayStringToInt(day);
        int dif = nextDay - oldDay;
        if (dif <= 0) {
            dif = 7;
        }
        cal.add(Calendar.DATE, dif);

        return cal.getTime();
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

    /**
     * Converts a String representation of a day into its numeric value;
     *
     * @param day
     * @return
     */
    public int dayStringToInt(String day) {
        int dayInt = 0;
        switch (day) {
            case "SUN":
                dayInt = Calendar.SUNDAY;
                break;
            case "MON":
                dayInt = Calendar.MONDAY;
                break;
            case "TUE":
                dayInt = Calendar.TUESDAY;
                break;
            case "WED":
                dayInt = Calendar.WEDNESDAY;
                break;
            case "THU":
                dayInt = Calendar.THURSDAY;
                break;
            case "FRI":
                dayInt = Calendar.FRIDAY;
                break;
            case "SAT":
                dayInt = Calendar.SATURDAY;
                break;
            default:
                break;
        }
        return dayInt;
    }

    /**
     * Converts a integer representation of a day into s String.
     *
     * @param day
     * @return
     */
    public String dayIntToString(int day) {
        String dayString = "";
        switch (day) {
            case Calendar.SUNDAY:
                dayString = "SUN";
                break;
            case Calendar.MONDAY:
                dayString = "MON";
                break;
            case Calendar.TUESDAY:
                dayString = "TUE";
                break;
            case Calendar.WEDNESDAY:
                dayString = "WED";
                break;
            case Calendar.THURSDAY:
                dayString = "THU";
                break;
            case Calendar.FRIDAY:
                dayString = "FRI";
                break;
            case Calendar.SATURDAY:
                dayString = "SAT";
                break;
            default:
                break;
        }
        return dayString;
    }

}
