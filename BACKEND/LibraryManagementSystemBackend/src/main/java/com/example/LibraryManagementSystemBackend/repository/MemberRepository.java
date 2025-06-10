package com.example.LibraryManagementSystemBackend.repository;

import com.example.LibraryManagementSystemBackend.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    @Query("SELECT m FROM Member m WHERE m.idRollNo = :idRollNo")
    Optional<Member> findByRollOrId(@Param("idRollNo") String idRollNo);

    @Query("SELECT m.idRollNo FROM Member m")
    List<String> findAllRollNumbers();

    long count();

}
