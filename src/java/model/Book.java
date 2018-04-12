/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Igor Nascimento <igornascimento@gmail.com>
 */
@XmlRootElement
public class Book implements Serializable {
    
    private String isbn;
    private String name;
    private List<Author> authors;
    private String editor;
    private int publishYear;

    public Book() {
    }

    public Book(String isbn, String name, List<Author> authors, String editor, int publishYear) {
        this.isbn = isbn;
        this.name = name;
        this.authors = authors;
        this.editor = editor;
        this.publishYear = publishYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    // alias for ISBN
    public String getCode() {
        return isbn;
    }

    public void setCode(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }
    
}
