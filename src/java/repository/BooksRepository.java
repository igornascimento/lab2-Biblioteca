/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import java.util.List;
import model.Author;
import model.Book;

/**
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
public class BooksRepository {

    private static BooksRepository instance;
    private List<Book> booksList = new ArrayList<>();
    
    
    private BooksRepository() {
        // populating for tests pourpose
        List<Author> authorsList = new ArrayList<>();
        authorsList.add(new Author("William", "Shakespeare", "Brasil"));
        booksList.add(new Book("8582850409", "Romeu e Julieta", authorsList, "Centaur Editions", 2013));
        
        authorsList.clear();
        authorsList.add(new Author("Machado de", "Assis", "Brasil"));
        booksList.add(new Book("856356093X", "O Alienista", authorsList, "Penguim", 2014));
        
        authorsList.clear();
        authorsList.add(new Author("José", "Saramago", "Portugal"));
        booksList.add(new Book("8571644950", "Ensaio sobre a cegueira", authorsList, "Companhia das Letras", 1995));
    }
    
    /**
     * The instance
     * @return RepositoryBooks instance
     */
    public static synchronized BooksRepository getInstance() {
        if (instance == null) {
            instance = new BooksRepository();
        }
        return instance;
    }
    
    /**
     * Adds a new book to the repository
     * @param Book b 
     */
    public void insertBook(Book b) {
        booksList.add(b);
    }
    
    /**
     * Returns the list of registered books
     * @return List<Book> booksList
     */
    public List<Book> list() {
        return booksList;
    }
    
    /**
     * Performs a search for a book based on ISBN code
     * @param String code
     * @return Book book
     */
    public Book searchByCode(String code) {
        for (Book b : booksList) {
            if (b.getIsbn().equals(code)) {
                List<Author> authorsList = new ArrayList<>();
                
                for (Author a : b.getAuthors()) {
                    authorsList.add(new Author(a.getName(), a.getSurname(), a.getCountry()));
                }
                return new Book(b.getIsbn(), b.getTitle(), authorsList, b.getEditor(), b.getPublishYear());
            }
        }
        return null;
    }
    
    /**
     * Performs a search for a book by its title
     * @param String title
     * @return Book book
     */
    public Book searchByTitle(String title) {
        for (Book b : booksList) {
            if (b.getTitle().contains(title)) {
                List<Author> authorsList = new ArrayList<>();
                
                for (Author a : b.getAuthors()) {
                    authorsList.add(new Author(a.getName(), a.getSurname(), a.getCountry()));
                }
                return new Book(b.getIsbn(), b.getTitle(), authorsList, b.getEditor(), b.getPublishYear());
            }
        }
        return null;
    }
    
    /**
     * Gets the book position on the array by its ISBN code
     * @param String code
     * @return index
     */
    public int getIndex(String code) {
        for (int i=0; i<booksList.size(); i++) {
            if (booksList.get(i).getIsbn().equals(code)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Updates a book by its ISBN code
     * @param b 
     */
    public void update(Book b) {
        booksList.set(this.getIndex(b.getIsbn()), b);
    }
    
    /**
     * Removes a book by its ISBN code
     * @param code 
     */
    public void remove(String code) {
        booksList.remove(this.getIndex(code));
    }
    
}
