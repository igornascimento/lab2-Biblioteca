/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import entity.Movimentation;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import util.JPAUtil;

/**
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
public class MovimentationRN {

    public MovimentationRN() {
    }
    
    public Movimentation insert(Movimentation mov) throws Exception {
        if (mov.getCustomer().getName().equals("") || mov.getBookList().isEmpty()) {
            throw new Exception("Dados informados inválidos");
        }
        Date dt = new Date();
        mov.setDate(dt);
        EntityManager manager = JPAUtil.createManager();
        manager.getTransaction().begin();
        manager.persist(mov);
        manager.getTransaction().commit();
        manager.close();
        return mov;
    }
    
    public Movimentation findById(int id) {
        EntityManager manager = JPAUtil.createManager();
        manager.getTransaction().begin();
        Movimentation mov = manager.find(Movimentation.class, id);
        manager.close();
        return mov;
    }
    
    public Movimentation update(Movimentation mov) {
        EntityManager manager = JPAUtil.createManager();
        Movimentation updated = manager.find(Movimentation.class, mov.getId());
        manager.getTransaction().begin();
        updated.setDate(mov.getDate());
        updated.setCustomer(mov.getCustomer());
        updated.setBookList(mov.getBookList());
        manager.getTransaction().commit();
        manager.close();
        return updated;
    }
    
    public Movimentation remove(int id) {
        EntityManager manager = JPAUtil.createManager();
        Movimentation mov = manager.find(Movimentation.class, id);
        manager.getTransaction().begin();
        manager.remove(mov);
        manager.getTransaction().commit();
        manager.close();
        return mov;
    }
    
    public List<Movimentation> list() {
        EntityManager manager = JPAUtil.createManager();
        Query query = manager.createQuery("SELECT m FROM Movimentation m");
        List<Movimentation> list = query.getResultList();
        manager.close();
        return list;
    }
}
