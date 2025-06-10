package com.example.LibraryManagementSystemBackend.service;

import com.example.LibraryManagementSystemBackend.exception.APIException;
import com.example.LibraryManagementSystemBackend.exception.ResourceNotFoundException;
import com.example.LibraryManagementSystemBackend.models.Member;
import com.example.LibraryManagementSystemBackend.models.Transaction;
import com.example.LibraryManagementSystemBackend.repository.MemberRepository;
import com.example.LibraryManagementSystemBackend.repository.TransectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TransectionRepository transectionRepository;
    public Member saveMember(Member member) {
        Optional<Member> existingMember = memberRepository.findByRollOrId(member.getIdRollNo());
        if (existingMember.isPresent()){
            throw new APIException("Member with Id "+member.getIdRollNo()+" is already present");
        }
        return memberRepository.save(member);
    }

    public List<Member> getAllMember() {
        List<Member> members = memberRepository.findAll();
        if (members.isEmpty()){
            throw  new APIException("No Member saved till now");
        }
        return members;
    }

    public Member getMemberById(Long id) {
        Optional<Member> existingMember =  memberRepository.findById(id);
        if (existingMember.isEmpty()){
            throw new APIException("Member not found in the database");
        }
        return existingMember.get();
    }

    public Member updateMember(Long id, Member member) {
        Member memberFromDB = memberRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Member","memberId",member.getId()));

        memberFromDB.setYear(member.getYear());
        memberFromDB.setName(member.getName());
        memberFromDB.setJoinDate(member.getJoinDate());
        memberFromDB.setType(member.getType());
        memberFromDB.setYear(member.getYear());
        memberFromDB.setIdRollNo(member.getIdRollNo());

        return memberRepository.save(memberFromDB);
    }

    public void deleteBook(Long id) {
        Optional<Member>  member = memberRepository.findById(id);
        if (member.isPresent()){
            memberRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("Member","memberId",id);
        }
    }

    public List<String> getAllMemberRoll() {
        return memberRepository.findAllRollNumbers();
    }

    public List<String> getAllIssueMemberRoll() {
        List<Transaction> transactions = transectionRepository.findAllExceptReturnType();
        Set<String> uniqueRolls = new HashSet<>();

        for (Transaction txn : transactions) {
            uniqueRolls.add(txn.getMember().getIdRollNo());
        }
        System.out.println(uniqueRolls);

        return new ArrayList<>(uniqueRolls); // Convert Set back to List if needed

    }
}
