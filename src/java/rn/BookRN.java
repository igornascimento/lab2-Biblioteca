/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rn;

import entity.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import util.JPAUtil;

/**
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
public class BookRN {
    
    public BookRN() {
    }
    
    public Book insert(Book book) throws Exception {
        if (book.getCode().equals("") || book.getTitle().equals("") || book.getEditor().equals("") || book.getAuthors() == null) {
            throw new Exception("Dados informados inválidos");
        }
        EntityManager manager = JPAUtil.createManager();
        manager.getTransaction().begin();
        manager.persist(book);
        manager.getTransaction().commit();
        manager.close();
        return book;
    }
    
    public Book findByCode(String code) {
        EntityManager manager = JPAUtil.createManager();
        manager.getTransaction().begin();
        Book book = manager.find(Book.class, code);
        manager.close();
        return book;
    }
    
    public Book update(Book book) {
        EntityManager manager = JPAUtil.createManager();
        Book updated = manager.find(Book.class, book.getCode());
        manager.getTransaction().begin();
        updated.setTitle(book.getTitle());
        updated.setEditor(book.getEditor());
        updated.setPublishYear(book.getPublishYear());
        updated.setAuthors(book.getAuthors());
        manager.getTransaction().commit();
        manager.close();
        return updated;
    }
    
    public Book remove(String code) {
        EntityManager manager = JPAUtil.createManager();
        Book book = manager.find(Book.class, code);
        manager.getTransaction().begin();
        manager.remove(book);
        manager.getTransaction().commit();
        manager.close();
        return book;
    }
    
    public List<Book> list() {
        EntityManager manager = JPAUtil.createManager();
        Query query = manager.createQuery("SELECT b FROM Book b");
        List<Book> list = query.getResultList();
        manager.close();
        return list;
    }
    
}