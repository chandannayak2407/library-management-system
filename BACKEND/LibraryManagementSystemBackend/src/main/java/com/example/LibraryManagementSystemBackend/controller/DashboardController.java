package com.example.LibraryManagementSystemBackend.controller;

import com.example.LibraryManagementSystemBackend.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;

    @GetMapping("totalBooks")
    public ResponseEntity<Integer> getTotalBooks(){
        Integer totalBooks = dashboardService.getTotalBooks();
        return new ResponseEntity<>(totalBooks, HttpStatus.OK);
    }
    @GetMapping("totalMembers")
    public ResponseEntity<Integer> getTotalMembers(){
        Integer totalMembers = dashboardService.getTotalMember();
        return new ResponseEntity<>(totalMembers, HttpStatus.OK);
    }
    @GetMapping("totalIssueBooks")
    public ResponseEntity<Integer> getTotalIssueBooks(){
        Integer totalIssueBooks = dashboardService.getTotalIssueBook();
        return new ResponseEntity<>(totalIssueBooks, HttpStatus.OK);
    }
}
