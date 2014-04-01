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
    public void addUser(User user){
        em.persist(user);
    }
}
