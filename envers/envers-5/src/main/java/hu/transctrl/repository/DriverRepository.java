package hu.transctrl.repository;

import hu.transctrl.entity.Driver;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by szilagyif on 2017.03.03..
 */
@Stateless
public class DriverRepository {

    @PersistenceContext(unitName="templatePU")
    EntityManager em;

    public void storeAndFlush(Driver d) {
        em.persist(d);
        em.flush();
    }
}