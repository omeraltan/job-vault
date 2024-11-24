package com.omer.candidate.service;

import com.omer.candidate.dto.CandidateDto;
import com.omer.candidate.dto.CandidateFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICandidateService {

    void createCandidate(CandidateDto candidateDto);

    void updateCandidate(Long id, CandidateDto candidateDto);

    boolean deleteCandidate(Long id);

    CandidateDto fetchCandidate(Long id);

    Page<CandidateDto> fetchAllCandidates(Pageable pageable);


    List<CandidateFilterDto> fetchCandidatesWithFilters(String positionType, String militaryStatus, String noticePeriod);
}
