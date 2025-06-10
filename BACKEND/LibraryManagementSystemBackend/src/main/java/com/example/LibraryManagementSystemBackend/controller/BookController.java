package com.example.LibraryManagementSystemBackend.controller;

import com.example.LibraryManagementSystemBackend.models.Book;
import com.example.LibraryManagementSystemBackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
         Book savedBook = bookService.saveBook(book);
         return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }
    @GetMapping("/recent")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books,HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book existingBook = bookService.getBookById(id);
        return new ResponseEntity<>(existingBook,HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book){
        Book updatedBook = bookService.updateBook(id,book);
        return new ResponseEntity<>(updatedBook,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return new ResponseEntity<>("Book deleted",HttpStatus.OK);
    }
    @GetMapping("/getAllBookName")
    public ResponseEntity<List<String>> getAllBookName(){
        List<String> allBookName = bookService.getAllBookName();
        return new ResponseEntity<>(allBookName,HttpStatus.OK);
    }
//    @GetMapping("/getAllIssueBookName")
//    public ResponseEntity<List<String>> getAllIssueBookName(){
//        List<String> allIssueBookName = bookService.getAllIssueBookName();
//        return new ResponseEntity<>(allIssueBookName,HttpStatus.OK);
//    }
    @GetMapping("/getIssuedBooksByMember/{roll}")
    public ResponseEntity<List<String>> getIssuedBooksByMember(@PathVariable String roll){
        List<String> bookNames = bookService.getIssuedBooksByMember(roll);
        return new ResponseEntity<>(bookNames,HttpStatus.OK);
    }


}
