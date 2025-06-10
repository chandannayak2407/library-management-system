package com.example.LibraryManagementSystemBackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnDetailsDTO {
    private Double amount;
    private Double fine;
    private Double totalAmount;
}
