/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import entity.Author;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import util.JPAUtil;

/**
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
public class AuthorRN {
    
    public AuthorRN() {
    }
    
    public Author insert(Author author) throws Exception {
        if (author.getName().equals("") || author.getSurname().equals("") || author.getCountry().equals("")) {
            throw new Exception("Dados informados inválidos");
        }
        EntityManager manager = JPAUtil.createManager();
        manager.getTransaction().begin();
        manager.persist(author);
        manager.getTransaction().commit();
        manager.close();
        return author;
    }
    
    public Author findById(int id) {
        EntityManager manager = JPAUtil.createManager();
        manager.getTransaction().begin();
        Author author = manager.find(Author.class, id);
        manager.close();
        return author;
    }
    
    public Author update(Author author) {
        EntityManager manager = JPAUtil.createManager();
        Author updated = manager.find(Author.class, author.getId());
        manager.getTransaction().begin();
        updated.setName(author.getName());
        updated.setSurname(author.getSurname());
        updated.setCountry(author.getCountry());
        manager.getTransaction().commit();
        manager.close();
        return updated;
    }
    
    public Author remove(int id) {
        EntityManager manager = JPAUtil.createManager();
        Author author = manager.find(Author.class, id);
        manager.getTransaction().begin();
        manager.remove(author);
        manager.getTransaction().commit();
        manager.close();
        return author;
    }
    
    public List<Author> list() {
        EntityManager manager = JPAUtil.createManager();
        Query query = manager.createQuery("SELECT a FROM Author a");
        List<Author> list = query.getResultList();
        manager.close();
        return list;
    }
    
}