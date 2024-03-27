/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.my.sasa.bistrovic.springbootelasticsearch;

import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Iterator<Book> getAllBooks() {
        return (Iterator<Book>) bookRepository.findAll().iterator();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    public void deleteBookById(String id) {
        bookRepository.deleteById(id);
    }
    
    public Book updateBookById(String id, Book updatedBook) {
        // Retrieve the existing book by ID
        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook == null) {
            // Handle case where book with given ID doesn't exist
            return null;
        }
        
        // Update the fields of the existing book with the values from the updated book
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());

        // Save the updated book
        return bookRepository.save(existingBook);
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id).orElse(null);
    }
}

