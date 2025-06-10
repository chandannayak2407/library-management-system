package com.example.LibraryManagementSystemBackend.controller;

import com.example.LibraryManagementSystemBackend.models.Book;
import com.example.LibraryManagementSystemBackend.models.Member;
import com.example.LibraryManagementSystemBackend.payload.ReturnDetailsDTO;
import com.example.LibraryManagementSystemBackend.payload.TransactionDTO;
import com.example.LibraryManagementSystemBackend.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;


    @GetMapping("/recent")
    public ResponseEntity<List<TransactionDTO>> getAllTransaction(){
        List<TransactionDTO> transactionDTOS = transactionService.getAllTransections();
        System.out.println("Returning transactions: " + transactionDTOS);
        return new ResponseEntity<>(transactionDTOS,HttpStatus.OK);
    }
    @PostMapping("/issue")
    public ResponseEntity<TransactionDTO> addIssueBook(@RequestBody TransactionDTO transactionDTO){

        System.out.println("Issue Date "+transactionDTO.getIssueDate());
        TransactionDTO savedTransactionDTO = transactionService.saveIssueBook(transactionDTO);
        System.out.println(savedTransactionDTO);
        return new ResponseEntity<>(savedTransactionDTO, HttpStatus.CREATED);
    }
    @PostMapping("/return")
    public ResponseEntity<TransactionDTO> returnBook(@RequestBody TransactionDTO transactionDTO){
        TransactionDTO savedReturnBook = transactionService.saveReturnBook(transactionDTO);
        return new ResponseEntity<>(savedReturnBook,HttpStatus.OK);
    }
    @GetMapping("/getReturnDetails")
    public ResponseEntity<Double> getReturnDetails(@RequestParam String book,
                                                             @RequestParam String member){
        System.out.println("Inside the getReturnDetails");
        Double fine = transactionService.getReturnDetails(book,member);
        return new ResponseEntity<>(fine,HttpStatus.OK);
    }

    @PutMapping("/update/{id}/{days}")
    public ResponseEntity<String> updateTransection(@PathVariable Long id,@PathVariable Integer days){

        System.out.println("Inside the updateTransection method");

        String message = transactionService.updateTransection(id,days);

        return new ResponseEntity<>(message,HttpStatus.OK);
    }

}
