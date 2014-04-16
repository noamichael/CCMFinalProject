package org.ccm.webcalendar.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import org.ccm.webcalendar.entity.DatabaseService;
import org.ccm.webcalendar.entity.User;

/**
 * A class which validates that new account information is valid.
 * @author Michael Kucinski
 */
@FacesValidator("registrationValidator")
public class RegistrationValidator implements Validator {

    @Inject
    private DatabaseService service;
    private String username;
    private String password;
    private String confirmationPassword;
    private final static String ERROR_MESSAGE = "Username already taken.";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        UIInput input = (UIInput) component.getAttributes().get("pass");
        username = (String) value;
        password = (String) input.getSubmittedValue();
        input = (UIInput) component.getAttributes().get("conPass");
        confirmationPassword = (String) input.getSubmittedValue();

        if (!username.equals(username.toLowerCase())) {
            msg.setSummary("Your username must be lowercase.");
            throw new ValidatorException(msg);
        }
        if (!password.equals(confirmationPassword)) {
            msg.setSummary("The two passwords do not match.");
            throw new ValidatorException(msg);
        }
        User foundUser = service.findUserByUsername(username);
        if (foundUser != null) {
            msg.setSummary(ERROR_MESSAGE);
            throw new ValidatorException(msg);
        }

    }

}
