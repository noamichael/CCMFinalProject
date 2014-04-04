package org.ccm.webcalendar.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Michael Kucinski/Trevor Florio
 */
@Entity
@Table(name="EVENT")
public class Event implements Comparable, Serializable {
    
    public static final int HIGH = 3;
    public static final int MEDIUM = 2;
    public static final int LOW = 1;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="NAME")
    @NotNull
    private String name;
    @Column(name="DESCRIPTION")    
    private String description;
    @Column(name="STARTDATE")
    @NotNull
    @Temporal(value=TemporalType.DATE)
    private Date startDate;
    @Temporal(value=TemporalType.DATE)
    @Column(name="ENDDATE")
    private Date endDate; 
    @Column(name="REPEATED")
    private boolean repeated;
    @Column(name="REPEATED_DAYS")
    private List<String> repeatedDays;
    @Column(name="LOCATION")
    private String location;
    @Column(name="PRIORITY")
    private int priority = LOW;
    @JoinColumn(name="OWNER")
    @OneToOne
    private User owner;
    
    public Event(){
            
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Event == false){
            throw new RuntimeException(String.format("Object [%s] is not an event!", o.getClass()));
        }
        
        Event e = (Event)o;
        
        if(e.getStartDate().equals(this.getStartDate())){
            return 0;
        }
        else if(e.getStartDate().before(this.getStartDate())){
            return -1;
            
        }
        else{
            return 1;
        }
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.getId());
        hash = 79 * hash + Objects.hashCode(this.getStartDate());
        hash = 79 * hash + Objects.hashCode(this.getEndDate());
        hash = 79 * hash + Objects.hashCode(this.getPriority());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.startDate, other.startDate)) {
            return false;
        }
        if (this.getPriority() != other.getPriority()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", name=" + name + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", location=" + location + ", priority=" + priority + ", userId=" + owner + '}';
    }
    


    /**
     * @return the owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * @param userId the owner to set
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the repeated
     */
    public boolean isRepeated() {
        return repeated;
    }

    /**
     * @param repeated the repeated to set
     */
    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    /**
     * @return the repeatedDays
     */
    public List<String> getRepeatedDays() {
        return repeatedDays;
    }

    /**
     * @param repeatedDays the repeatedDays to set
     */
    public void setRepeatedDays(List<String> repeatedDays) {
        this.repeatedDays = repeatedDays;
    }
    public boolean isEveryDay(){
        return repeatedDays == null ? false: repeatedDays.contains("ALL");
    }
  
}
