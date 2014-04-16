package org.ccm.webcalendar.validator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
 * A class which validates that the user credentials are valid.
 * @author Michael Kucinski
 */
@FacesValidator("loginValidator")
public class LoginValidator implements Validator {

    @Inject
    private DatabaseService service;
    private String username;
    private String password;
    private final static String ERROR_MESSAGE = "Incorrect username or password.";

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        UIInput input = (UIInput) component.getAttributes().get("pass");
        username = (String) value;
        password = (String) input.getSubmittedValue();
 
        User foundUser = service.findUserByUsername(username);
        if (foundUser == null) {
            msg.setSummary(ERROR_MESSAGE);
            throw new ValidatorException(msg);
        }
        password = hashPass(password);
        if (!foundUser.getPassword().equals(password)) {
            msg.setSummary(ERROR_MESSAGE);
            throw new ValidatorException(msg);
        }

    }

    public String hashPass(String pass) {
        byte[] bytes;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(pass.getBytes());
            bytes = md.digest();
            String sb = "";
            for (byte b : bytes) {
                sb += Integer.toHexString((int) (b & 0xff));
            }
            pass = sb;
        } catch (NoSuchAlgorithmException ex) {

        }
        return pass;
    }

}
