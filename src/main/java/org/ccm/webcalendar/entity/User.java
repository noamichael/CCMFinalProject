package org.ccm.webcalendar.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Trevor Florio
 */
@Entity
@Table(name="USER_TABLE")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="USERNAME",unique=true)
    @NotNull
    private String username;
    @Column(name="PASSWORD")
    @NotNull
    private String password;
    @OneToMany(mappedBy="userId", cascade = CascadeType.ALL)
    private List<Event> events;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the events
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * @param events the events to set
     */
    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void sortByDate(){
        
        List<Event> out = new ArrayList<>();
        
        for(int i=0;i<events.size();i++){
            
            Event soonest =events.get(0);
            for(int j=0;j<events.size();j++){
                if(events.get(i).getStartDate().before(soonest.getStartDate())){
                    soonest=events.get(i);
                }
            }
            out.add(soonest);
            
        }
        events=out;
        
    }
    
    
}
