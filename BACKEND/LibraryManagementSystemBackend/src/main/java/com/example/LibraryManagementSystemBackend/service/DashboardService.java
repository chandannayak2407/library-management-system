package com.example.LibraryManagementSystemBackend.service;

import com.example.LibraryManagementSystemBackend.repository.BookRepository;
import com.example.LibraryManagementSystemBackend.repository.MemberRepository;
import com.example.LibraryManagementSystemBackend.repository.TransectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TransectionRepository transectionRepository;
    public Integer getTotalBooks() {
        return Math.toIntExact(bookRepository.count());
    }
    public Integer getTotalMember(){
        return Math.toIntExact(memberRepository.count());
    }
    public Integer getTotalIssueBook(){
        return Math.toIntExact(transectionRepository.countDistinctBooksExceptReturn());
    }
}
