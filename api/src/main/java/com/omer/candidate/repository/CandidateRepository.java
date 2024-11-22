package com.omer.candidate.repository;

import com.omer.candidate.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long>, JpaSpecificationExecutor<Candidate> {

    Optional<Candidate> findByEmail(String email);

    Optional<Candidate> findByPhone(String phone);


}
