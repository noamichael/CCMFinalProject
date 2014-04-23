package org.ccm.webcalendar;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;

/**
 *
 * @author Michael Kucinski
 */
@Named
@SessionScoped
public class ErrorController implements Serializable {

    private String trace;

    public ErrorController() {
       
    }

    public String getStackTrace() {
        // Get the current JSF context
        FacesContext context = FacesContext.getCurrentInstance();
        Map requestMap = context.getExternalContext().getRequestMap();

        // Fetch the exception
        Throwable ex = (Throwable) requestMap.get("javax.servlet.error.exception");

        // Create a writer for keeping the stacktrace of the exception
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);

        // Fill the stack trace into the write
        fillStackTrace(ex, pw);
        trace = writer.toString();
        return trace;
    }

    public void doCheck() {
         System.out.println("Checking!");
        if (trace == null || trace.isEmpty()) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
            } catch (IOException e) {
                Logger.getLogger(ErrorController.class.getName()).log(Level.SEVERE, null, e);
            }

        }
        else{
            trace = null;
        }
    }

    /**
     * Write the stack trace from an exception into a writer.
     *
     * @param ex Exception for which to get the stack trace
     * @param pw PrintWriter to write the stack trace
     */
    private void fillStackTrace(Throwable ex, PrintWriter pw) {
        if (null == ex) {
            return;
        }

        ex.printStackTrace(pw);

        // The first time fillStackTrace is called it will always
        //  be a ServletException
        if (ex instanceof ServletException) {
            Throwable cause = ((ServletException) ex).getRootCause();
            if (null != cause) {
                pw.println("Root Cause:");
                fillStackTrace(cause, pw);
            }
        } else {
            // Embedded cause inside the ServletException
            Throwable cause = ex.getCause();

            if (null != cause) {
                pw.println("Cause:");
                fillStackTrace(cause, pw);
            }
        }
    }

}
