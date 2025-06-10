package com.example.LibraryManagementSystemBackend.service;

import com.example.LibraryManagementSystemBackend.exception.APIException;
import com.example.LibraryManagementSystemBackend.exception.ResourceNotFoundException;
import com.example.LibraryManagementSystemBackend.models.Book;
import com.example.LibraryManagementSystemBackend.models.Transaction;
import com.example.LibraryManagementSystemBackend.repository.BookRepository;
import com.example.LibraryManagementSystemBackend.repository.TransectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TransectionRepository transectionRepository;

    public Book saveBook(Book book) {
        Optional<Book> existingBook = bookRepository.findByNameIgnoreCaseAndAuthorIgnoreCase(book.getName(),book.getAuthor());
        if (existingBook.isPresent()){
            throw new APIException("Book with name "+book.getName()+" and author "+book.getAuthor()+" is already present");
        }
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()){
            throw  new APIException("No Book saved till now");
        }
        return books;
    }

    public Book getBookById(Long id) {
        Optional<Book> existingBook =  bookRepository.findById(id);
        if (existingBook.isEmpty()){
            throw new APIException("Book not found in the database");
        }
        return existingBook.get();
    }

    public Book updateBook(Long id, Book book) {
        Book bookFromDB = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "bookId", id));

        // DO NOT update ID
        bookFromDB.setName(book.getName());
        bookFromDB.setAuthor(book.getAuthor());
        bookFromDB.setPublisher(book.getPublisher());
        bookFromDB.setYear(book.getYear());
        bookFromDB.setEdition(book.getEdition());
        bookFromDB.setAccessionNo(book.getAccessionNo());
        bookFromDB.setCallNo(book.getCallNo());
        bookFromDB.setIsbn(book.getIsbn());
        bookFromDB.setPages(book.getPages());
        bookFromDB.setPictures(book.getPictures());
        bookFromDB.setMaps(book.getMaps());
        bookFromDB.setQuantity(book.getQuantity());
        bookFromDB.setDate(book.getDate());

        return bookRepository.save(bookFromDB);
    }

    public void deleteBook(Long id) {
        Optional<Book>  book = bookRepository.findById(id);

        if (book.isPresent()){
            Optional<Transaction> transaction = transectionRepository.findActiveTransactionByBook(book.get());
            if (transaction.isPresent()){
                throw new APIException("Can't delete, one member issued this book");
            }
            bookRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("Book","bookId",id);
        }
    }

    public List<String> getAllBookName() {
//        List<Book> books = bookRepository.findAll();
        //        for (int i = 0;i<books.size();i++){
//            String book = books.get(i).getName();
//            bookNames.add(book);
//        }
        return bookRepository.findAllBookNames();

    }

    public List<String> getAllIssueBookName() {
        List<Transaction> transactions = transectionRepository.findByType("Issue");
        List<String> allBookName = new ArrayList<>();
        for (int i = 0;i<transactions.size();i++){
            String bookName = transactions.get(i).getBook().getName();
            allBookName.add(bookName);
        }
        return allBookName;
    }

    public List<String> getIssuedBooksByMember(String roll) {
        return transectionRepository.findActiveBookNamesByMemberRoll(roll);

    }
}
