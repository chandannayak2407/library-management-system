package com.example.LibraryManagementSystemBackend.repository;

import com.example.LibraryManagementSystemBackend.models.Book;
import com.example.LibraryManagementSystemBackend.models.Member;
import com.example.LibraryManagementSystemBackend.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransectionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByType(String type);

    @Query("SELECT t.book.name FROM Transaction t WHERE t.member.idRollNo = :idRollNo AND t.type IN ('Issue', 'Renewal')")
    List<String> findActiveBookNamesByMemberRoll(@Param("idRollNo") String idRollNo);


    boolean existsByBookAndMemberAndType(Book book, Member member, String type);

    @Query("SELECT t FROM Transaction t WHERE t.book.name = :bookName AND t.member.idRollNo = :roll AND t.type = :type")
    Optional<Transaction> findByBookNameAndMemberIdRollNoAndType(@Param("bookName") String bookName,
                                                                 @Param("roll") String roll,
                                                                 @Param("type") String type);

    @Query("SELECT t FROM Transaction t WHERE t.book.name = :bookName AND t.member.idRollNo = :roll AND t.type <> 'Return'")
    Optional<Transaction> findByBookNameAndMemberIdRollNoExcludingReturn(@Param("bookName") String bookName,
                                                                         @Param("roll") String roll);



    @Query("SELECT t FROM Transaction t WHERE t.type <> 'Return'")
    List<Transaction> findAllExceptReturnType();


    @Query("SELECT COUNT(DISTINCT t.book.id) FROM Transaction t WHERE t.type <> 'Return'")
    long countDistinctBooksExceptReturn();


    Optional<Transaction> findByBook(Book book);

    @Query("SELECT t FROM Transaction t WHERE t.book = :book AND t.type IN ('Issue', 'Renewal')")
    Optional<Transaction> findActiveTransactionByBook(@Param("book") Book book);

}
