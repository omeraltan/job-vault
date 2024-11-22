package com.omer.candidate.service;

import com.omer.candidate.dto.CandidateDto;

import java.util.List;

public interface ICandidateService {

    void createCandidate(CandidateDto candidateDto);

    void updateCandidate(Long id, CandidateDto candidateDto);

    boolean deleteCandidate(Long id);

    CandidateDto fetchCandidate(Long id);

    List<CandidateDto> fetchAllCandidates();

    List<CandidateDto> fetchCandidatesWithFilters(String positionType, String militaryStatus, String noticePeriod);
}
