package com.example.LibraryManagementSystemBackend.repository;

import com.example.LibraryManagementSystemBackend.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByNameIgnoreCaseAndAuthorIgnoreCase(String name, String author);

    @Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<Book> searchByNameAndAuthorLike(@Param("name") String name, @Param("author") String author);


    Optional<Book> findByName(String bookName);

    @Query("SELECT b.name FROM Book b")
    List<String> findAllBookNames();

    long count();
}
