/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccm.webcalendar.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import org.ccm.webcalendar.entity.DatabaseService;


/**
 *
 * @author Trevor Florio
 */
@FacesValidator("eventValidator")
public class EventValidator implements Validator {

    @Inject
    private DatabaseService service; //TO:DO -- ADD A CHECK TO PREVENT/WARN OF OVERLAPPING EVENTS
    private Date startDate;
    private Date endDate;

    private static final String ERROR = "Event cannot end before it starts.";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        msg.setSummary(ERROR);
        UIInput input = (UIInput) component.getAttributes().get("end");
        startDate = (Date) value;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String ed = (String) input.getSubmittedValue();

        try {
            endDate = sdf.parse(ed);
        } catch (ParseException ex) {
            Logger.getLogger(EventValidator.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (startDate != null && endDate != null) {
            if (startDate.after(endDate)) {
                throw new ValidatorException(msg);
            }
        }

    }

}
