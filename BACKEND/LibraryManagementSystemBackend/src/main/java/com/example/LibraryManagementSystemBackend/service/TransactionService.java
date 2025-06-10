package com.example.LibraryManagementSystemBackend.service;

import com.example.LibraryManagementSystemBackend.exception.APIException;
import com.example.LibraryManagementSystemBackend.exception.ResourceNotFoundException;
import com.example.LibraryManagementSystemBackend.models.Book;
import com.example.LibraryManagementSystemBackend.models.Member;
import com.example.LibraryManagementSystemBackend.models.Transaction;
import com.example.LibraryManagementSystemBackend.payload.ReturnDetailsDTO;
import com.example.LibraryManagementSystemBackend.payload.TransactionDTO;
import com.example.LibraryManagementSystemBackend.repository.BookRepository;
import com.example.LibraryManagementSystemBackend.repository.MemberRepository;
import com.example.LibraryManagementSystemBackend.repository.TransectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    TransectionRepository transectionRepository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BookRepository bookRepository;
    public TransactionDTO saveIssueBook(TransactionDTO transactionDTO) {

            // check the book is available or not
            Optional<Book> bookFromDB = bookRepository.findByName(transactionDTO.getBook());
            if (bookFromDB.isEmpty()){
                throw new ResourceNotFoundException("Book","Book Name",transactionDTO.getBook());
            }
            if (bookFromDB.get().getQuantity() == 0){
                throw new APIException("This book is unavailable. All copies have been issued.");
            }
            // check the member is available or not
            Optional<Member> memberFromDB = memberRepository.findByRollOrId(transactionDTO.getMember());
            if (memberFromDB.isEmpty()){
                throw new ResourceNotFoundException("Member","Member Roll",transactionDTO.getMember());
            }

        // 🔴 Check if this member already issued this book
        boolean alreadyIssued = transectionRepository.existsByBookAndMemberAndType(
                bookFromDB.get(), memberFromDB.get(), "Issue");

        if (alreadyIssued) {
            throw new APIException("This member has already issued this book.");
        }

            Transaction transaction = new Transaction();

            transaction.setType(transactionDTO.getType());
            transaction.setBook(bookFromDB.get());
            transaction.setMember(memberFromDB.get());
            transaction.setIssueDate(transactionDTO.getIssueDate());
//            transaction.setReturnDate(getReturnDate(transactionDTO.getIssueDate(),transactionDTO.getRentalDays()));
            transaction.setFine(transactionDTO.getFine());
            transaction.setRentalDays(transactionDTO.getRentalDays());
            System.out.println(transactionDTO.getRentalDays());
            Transaction savedTransection = transectionRepository.save(transaction);
            transactionDTO.setTransactionId(savedTransection.getId());

            // update Book quantity
            int currentQuantity = bookFromDB.get().getQuantity();
            bookFromDB.get().setQuantity(currentQuantity-1);

            bookRepository.save(bookFromDB.get());

            return transactionDTO;


    }

    public List<TransactionDTO> getAllTransections() {
        List<Transaction> transactions = transectionRepository.findAllExceptReturnType();
        if (transactions.isEmpty()){
            throw  new APIException("No transection saved till now");
        }
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        for(int i = 0; i<transactions.size();i++){
            TransactionDTO transactionDTO = new TransactionDTO();

            transactionDTO.setTransactionId(transactions.get(i).getId());
            transactionDTO.setType(transactions.get(i).getType());
            transactionDTO.setBook(transactions.get(i).getBook().getName());
            transactionDTO.setMember(transactions.get(i).getMember().getIdRollNo());
            transactionDTO.setIssueDate(transactions.get(i).getIssueDate());
            transactionDTO.setFine(transactions.get(i).getFine());
            transactionDTO.setRentalDays(transactions.get(i).getRentalDays());

            transactionDTOS.add(transactionDTO);
        }
        return transactionDTOS;

    }
    public String getReturnDate(String dateStr,Integer days){
        // Parse the string to a LocalDate
        LocalDate date = LocalDate.parse(dateStr);

        LocalDate updatedDate = date.plusDays(days);

        // Format back to String
        return updatedDate.toString();

    }

    public Double getReturnDetails(String book, String member) {
        // Fetch transaction by book + member
        Transaction txn = transectionRepository.findByBookNameAndMemberIdRollNoExcludingReturn(book, member)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "Book & Member", book + " & " + member));

        return getFineAmount(txn.getIssueDate(),txn.getRentalDays());
    }

    double getFineAmount(String inputDateString,Integer rentalDays){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate inputDate = LocalDate.parse(inputDateString, formatter);
        LocalDate today = LocalDate.now(); // current system date

        long daysDifference = ChronoUnit.DAYS.between(inputDate, today);

        if ((int) daysDifference < rentalDays){
            return 0;
        }
        return (daysDifference-rentalDays)*20;

    }

    public TransactionDTO saveReturnBook(TransactionDTO transactionDTO) {
        Transaction txn = transectionRepository.findByBookNameAndMemberIdRollNoExcludingReturn(transactionDTO.getBook(), transactionDTO.getMember())
                .orElseThrow(() -> new ResourceNotFoundException("Transaction", "Book & Member", transactionDTO.getBook() + " & " + transactionDTO.getMember()));

//        Optional<Book> bookFromDB = bookRepository.findByName(transactionDTO.getBook());
//        if (bookFromDB.isEmpty()){
//            throw new ResourceNotFoundException("Book","Book Name",transactionDTO.getBook());
//        }
//        int currentQuantity = bookFromDB.get().getQuantity();
//        bookFromDB.get().setQuantity(currentQuantity+1);
//
//        txn.setReturnDate(String.valueOf(LocalDate.now()));
//        System.out.println(transactionDTO.getFine());
//        txn.setFine(transactionDTO.getFine());
//        txn.setType("Return");
//
//        transectionRepository.save(txn);
        transectionRepository.delete(txn);

        return transactionDTO;
    }

    public String updateTransection(Long id, Integer days) {

        Optional<Transaction> transaction = transectionRepository.findById(id);
        if (transaction.isEmpty()){
            throw new APIException("Transection not found");
        }

        int currentRentalDays = transaction.get().getRentalDays();
        transaction.get().setRentalDays(currentRentalDays + days);
        transaction.get().setType("Renewal");
        transectionRepository.save(transaction.get());

        return "Date extended successfully";

    }
}
