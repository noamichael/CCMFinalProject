package org.ccm.webcalendar.entity;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * A stateless class which interacts with the database.
 *
 * @author Michael Kucinski
 */
@Stateless
public class DatabaseService {

    @PersistenceContext(unitName = "WebCalendarPU")
    private EntityManager em;

    /**
     *
     * @return All of the current users
     */
    public List<User> getAllUsers() {
        Query q = em.createQuery("SELECT U FROM User U");
        return q.getResultList();
    }

    /**
     * Returns all of the events within a range, inclusive
     *
     * @param startDate the beginning of the range, inclusive
     * @param endDate the end of the range, inclusive
     * @return a list of all of the dates within that range
     */
    public List<Date> findOverlappingEvents(Date startDate, Date endDate) {
        Query q = em.createQuery("SELECT E FROM Event E WHERE E.startDate >= :startDate AND E.endDate <= :endDate");
        q.setParameter("startDate", startDate);
        q.setParameter("endDate", endDate);
        return q.getResultList();
    }

    /**
     * Finds a user by username.
     *
     * @param username
     * @return The first occurrence of that user
     */
    public User findUserByUsername(String username) {
        Query q = em.createQuery("SELECT DISTINCT U from User U WHERE U.username = :username");
        q.setParameter("username", username);

        List<User> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * Finds a user by username. The difference between this and the
     * findByUsernameMethod is that this method returns a user without the
     * password for security.
     *
     * @param username
     * @return the User object by username without a password.
     */
    public User loginByUsername(String username) {
        Query q = em.createQuery("SELECT DISTINCT NEW org.ccm.webcalendar.entity.User(U.username, U.id) from User U WHERE U.username = :username");
        q.setParameter("username", username);

        List<User> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    /**
     * Finds all events by username
     *
     * @param username
     * @return
     */
    public List<Event> findEventByUsername(String username) {
        Query q = em.createQuery("SELECT E from Event E WHERE E.owner.username = :username");
        q.setParameter("username", username);
        return q.getResultList();
    }

    /**
     * Finds sorted events, sorted by date.
     *
     * @param username
     * @return
     */
    public List<Event> findDateSortedEventByUsername(String username) {
        Query q = em.createQuery("SELECT E from Event E WHERE E.owner.username = :username ORDER BY E.startDate ASC");
        q.setParameter("username", username);
        return q.getResultList();
    }

    /**
     * Finds sorted events, sorted by priority
     *
     * @param username
     * @return
     */
    public List<Event> findPrioritySortedEventByUsername(String username) {
        Query q = em.createQuery("SELECT E from Event E WHERE E.owner.username = :username ORDER BY  E.priority DESC");
        q.setParameter("username", username);
        return q.getResultList();
    }

    /**
     * Adds a user to the persistence context.
     *
     * @param user
     */
    public void addUser(User user) {
        em.persist(user);
    }

    /**
     * Adds an event to the persistence context
     *
     * @param event
     */
    public void addEvent(Event event) {
        em.persist(event);
    }

    /**
     * Updates users in the persistence context.
     *
     * @param user
     */
    public void updateUser(User user) {
        User oldUser = em.find(User.class, user.getId());
        oldUser.updateUser(user);

    }

    /**
     * Removes a user from the persistence context.
     *
     * @param user
     */
    public void remove(User user) {
        User removed = em.getReference(User.class, user.getId());
        em.remove(removed);
    }

    /**
     * Removes an event from the persistence context.
     * @param event 
     */
    public void removeEvent(Event event) {
        Event removed = em.getReference(Event.class, event.getPk());
        em.remove(removed);
    }

}
