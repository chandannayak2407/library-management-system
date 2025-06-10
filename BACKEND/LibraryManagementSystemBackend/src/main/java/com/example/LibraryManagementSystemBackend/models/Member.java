package com.example.LibraryManagementSystemBackend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;
    @Column(name = "roll_or_id")
    private String idRollNo;
    private String type; // Student, Faculty, Staff, Admin, Others
    private String year;
    private String joinDate;
}
