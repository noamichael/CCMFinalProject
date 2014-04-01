package org.ccm.webcalendar.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Event implements Serializable, Comparable {
    
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
    @Temporal(value=TemporalType.DATE)
    private Date startDate;
    @Temporal(value=TemporalType.DATE)
    @Column(name="ENDDATE")
    private Date endDate; 
    @Column(name="PRIORITY")
    private int priority = LOW;
    @ManyToOne
    private User userId;

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
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.startDate);
        hash = 79 * hash + Objects.hashCode(this.endDate);
        hash = 79 * hash + Objects.hashCode(this.priority);
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
        if (this.priority != other.priority) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", name=" + name + ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", priority=" + priority + '}';
    }
    
    
    
}
