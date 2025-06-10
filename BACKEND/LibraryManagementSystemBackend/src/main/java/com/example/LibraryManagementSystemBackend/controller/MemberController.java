package com.example.LibraryManagementSystemBackend.controller;

import com.example.LibraryManagementSystemBackend.models.Book;
import com.example.LibraryManagementSystemBackend.models.Member;
import com.example.LibraryManagementSystemBackend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @PostMapping("/add")
    public ResponseEntity<Member> addMember(@RequestBody Member member){
        Member savedMember = memberService.saveMember(member);
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }
    @GetMapping("/recent")
    public ResponseEntity<List<Member>> getAllMember(){
        List<Member> members = memberService.getAllMember();
        return new ResponseEntity<>(members,HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member existingMember = memberService.getMemberById(id);
        return new ResponseEntity<>(existingMember,HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @RequestBody Member member){
        Member updatedMember = memberService.updateMember(id,member);
        return new ResponseEntity<>(updatedMember,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id){
        memberService.deleteBook(id);
        return new ResponseEntity<>("Book deleted",HttpStatus.OK);
    }
    @GetMapping("/getAllMemberRoll")
    public ResponseEntity<List<String>> getAllMemberRoll(){
        System.out.println("getAllMemberRoll");
        List<String> allMemberRoll = memberService.getAllMemberRoll();
        return new ResponseEntity<>(allMemberRoll,HttpStatus.OK);
    }
    @GetMapping("/getAllIssueMemberRoll")
    public ResponseEntity<List<String>> getAllIssueMemberRoll(){
        System.out.println("getAllIssueMemberRoll");
        List<String> allIssueMemberRoll = memberService.getAllIssueMemberRoll();
        return new ResponseEntity<>(allIssueMemberRoll,HttpStatus.OK);
    }
}
