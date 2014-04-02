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
    @PersistenceContext(unitName="WebCalendarPU")
    private EntityManager em;
    
    public List<User> getAllUsers(){
        Query q = em.createQuery("SELECT U FROM User U");
        return q.getResultList();
    }
    public User findUserByUsername(String username){
        Query q = em.createQuery("SELECT U from User U WHERE U.username = :username", User.class);
        q.setParameter("username", username);
        List<User> result = q.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
    public void addUser(User user){
        em.persist(user);
    }
    public void updateUser(User user){
        User oldUser = em.find(User.class, user.getId());
        oldUser.updateUser(user);
       
    }
    public void removeUser(User user){
        em.remove(user);
    }
}
