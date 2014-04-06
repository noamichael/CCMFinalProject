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
import org.primefaces.model.ScheduleEvent;

/**
 *
 * @author Michael Kucinski/Trevor Florio
 */
@Entity
@Table(name = "EVENT")
public class Event implements Comparable, Serializable, ScheduleEvent {

    private static final int HIGH = 3;
    private static final int MEDIUM = 2;
    private static final int LOW = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK")
    private Long pk;
    @Column(name = "NAME")
    @NotNull
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "ALL_DAY")
    private boolean allDay;
    @Column(name = "ENABLED")
    private boolean enabled;
    @Column(name = "STARTDATE")
    @Temporal(value = TemporalType.DATE)
    private Date startDate;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "ENDDATE")
    private Date endDate;
    @Column(name = "REPEATED")
    private boolean repeated;
    @Column(name = "STYLE")
    private String styleClass;
    @Column(name = "ID")
    private String id;
    @Column(name = "REPEATED_DAYS")
    private List<String> repeatedDays;
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "PRIORITY")
    private int priority = LOW;
    @JoinColumn(name = "OWNER")
    @OneToOne
    private User owner;

    public Event() {

    }

    public Event(String title, String description, Date startDate, Date endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return the id
     */
    public Long getPk() {
        return pk;
    }

    /**
     * @return the Title
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
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
    @Override
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
    @Override
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
     * @param pk
     */
    public void setPk(Long pk) {
        this.pk = pk;
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
        if (o instanceof Event == false) {
            throw new RuntimeException(String.format("Object [%s] is not an event!", o.getClass()));
        }

        Event e = (Event) o;

        if (e.getStartDate().equals(this.getStartDate())) {
            return 0;
        } else if (e.getStartDate().before(this.getStartDate())) {
            return -1;

        } else {
            return 1;
        }

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.getPk());
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
        if (!Objects.equals(this.pk, other.pk)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
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
        return "Event{" + "pk=" + getPk() + ", title=" + getTitle() + ", description=" + getDescription() + ", startDate=" + getStartDate() + ", endDate=" + getEndDate() + ", location=" + getLocation() + ", priority=" + getPriority() + ", userId=" + getOwner() + '}';
    }

    /**
     * @return the owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * t
     *
     * @param owner
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

    public boolean isEveryDay() {
        return getRepeatedDays() == null ? false : getRepeatedDays().contains("ALL");
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Object getData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAllDay() {
        return this.allDay;
    }

    @Override
    public String getStyleClass() {
        return this.styleClass;
    }

    @Override
    public boolean isEditable() {
        return this.isEnabled();
    }

    /**
     * @param allDay the allDay to set
     */
    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @param styleClass the styleClass to set
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

}
