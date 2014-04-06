package org.ccm.webcalendar;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.ccm.webcalendar.entity.DatabaseService;
import org.ccm.webcalendar.entity.Event;

/**
 *
 * @author Michael Kucinski/Trevor Florio
 */
@Named
@SessionScoped
public class CalendarController implements Serializable {
    @Inject
    private DatabaseService service;
    private List<Event> currentEvents;
    public CalendarController(){

    }
    
}
