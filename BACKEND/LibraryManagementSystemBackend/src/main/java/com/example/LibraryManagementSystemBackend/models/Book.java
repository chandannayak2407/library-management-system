package com.example.LibraryManagementSystemBackend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;
    private String name;
    private String author;
    private String publisher;
    private Integer year;
    private String edition;
    private String accessionNo;
    private String callNo;
    private String isbn;
    private Integer pages;
    private Integer pictures;
    private Integer maps;
    private Integer quantity;
}
