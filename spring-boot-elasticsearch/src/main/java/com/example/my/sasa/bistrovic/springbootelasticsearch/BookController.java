/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.my.sasa.bistrovic.springbootelasticsearch;

import java.util.Collection;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public Iterator<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable String id) {
        return bookService.getBookById(id);
    }
    
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable String id) {
        bookService.deleteBookById(id);
    }
    
    @PutMapping("/{id}")
    public Book updateBookById(@PathVariable String id, @RequestBody Book updateBook) {
        return bookService.updateBookById(id, updateBook);
    }

    @PostMapping
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }
}

