package org.ccm.webcalendar.entity;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Michael Kucinski
 */
@Stateless
public class DatabaseService {

    @PersistenceContext(unitName = "WebCalendarPU")
    private EntityManager em;

    public List<User> getAllUsers() {
        Query q = em.createQuery("SELECT U FROM User U");
        return q.getResultList();
    }

    public User findUserByUsername(String username) {
        Query q = em.createQuery("SELECT DISTINCT U from User U WHERE U.username = :username");
        q.setParameter("username", username);

        List<User> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public List<Event> findEventByUsername(String username) {
        Query q = em.createQuery("SELECT E from Event E WHERE E.owner.username = :username");
        q.setParameter("username", username);
        return q.getResultList();
    }

    public void addUser(User entity) {
        em.persist(entity);
    }
    public void addEvent(Event event){
        em.persist(event);
    }

    public void updateUser(User user) {
        User oldUser = em.find(User.class, user.getId());
        oldUser.updateUser(user);

    }

    public void remove(Event entity) {
        em.merge(entity);
        em.remove(entity);
    }

}
