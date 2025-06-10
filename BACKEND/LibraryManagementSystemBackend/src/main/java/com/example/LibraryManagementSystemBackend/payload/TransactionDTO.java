package com.example.LibraryManagementSystemBackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private Long transactionId;
    private String type;
    private String book;
    private String member;
    private String issueDate;
    private String returnDate;
//    private Double amount;
    private Double fine;
    private Integer rentalDays;
//    private Double perDayAmount;
}
