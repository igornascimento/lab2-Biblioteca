/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import entity.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import util.JPAUtil;

/**
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
public class CustomerRN {
    
    public CustomerRN() {
    }
    
    public Customer insert(Customer customer) throws Exception {
        if (customer.getName().equals("") || customer.getPhone().equals("")) {
            throw new Exception("Dados informados inválidos");
        }
        EntityManager manager = JPAUtil.createManager();
        manager.getTransaction().begin();
        manager.persist(customer);
        manager.getTransaction().commit();
        manager.close();
        return customer;
    }
    
    public Customer findById(int id) {
        EntityManager manager = JPAUtil.createManager();
        manager.getTransaction().begin();
        Customer customer = manager.find(Customer.class, id);
        manager.close();
        return customer;
    }
    
    public Customer update(Customer customer) {
        EntityManager manager = JPAUtil.createManager();
        Customer updated = manager.find(Customer.class, customer.getId());
        manager.getTransaction().begin();
        updated.setName(customer.getName());
        updated.setPhone(customer.getPhone());
        manager.getTransaction().commit();
        manager.close();
        return updated;
    }
    
    public Customer remove(int id) {
        EntityManager manager = JPAUtil.createManager();
        Customer customer = manager.find(Customer.class, id);
        manager.getTransaction().begin();
        manager.remove(customer);
        manager.getTransaction().commit();
        manager.close();
        return customer;
    }
    
    public List<Customer> list() {
        EntityManager manager = JPAUtil.createManager();
        Query query = manager.createQuery("SELECT c FROM Customer c");
        List<Customer> list = query.getResultList();
        manager.close();
        return list;
    }
    
}
