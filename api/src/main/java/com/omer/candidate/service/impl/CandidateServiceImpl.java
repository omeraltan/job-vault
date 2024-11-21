package com.omer.candidate.service.impl;

import com.omer.candidate.dto.CandidateDto;
import com.omer.candidate.entity.Candidate;
import com.omer.candidate.exception.EmailAlreadyExistException;
import com.omer.candidate.exception.PhoneAlreadyExistException;
import com.omer.candidate.mapper.CandidateMapper;
import com.omer.candidate.repository.CandidateRepository;
import com.omer.candidate.service.ICandidateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements ICandidateService {

    private CandidateRepository candidateRepository;
    private CandidateMapper candidateMapper;

    @Override
    public void createCandidate(CandidateDto candidateDto) {
        Optional<Candidate> emailCandidate = candidateRepository.findByEmail(candidateDto.getEmail());
        if (emailCandidate.isPresent()) {
            throw new EmailAlreadyExistException("Email already exists : " + candidateDto.getEmail());
        }

        Optional<Candidate> phoneCandidate = candidateRepository.findByPhone(candidateDto.getPhone());
        if (phoneCandidate.isPresent()) {
            throw new PhoneAlreadyExistException("Phone already exists : " + candidateDto.getPhone());
        }

        candidateRepository.save(candidateMapper.toCandidate(candidateDto));
    }

    @Override
    public void updateCandidate(CandidateDto candidateDto) {

    }

    @Override
    public void deleteCandidate(Long id) {

    }

    @Override
    public CandidateDto fetchCandidate(Long id) {
        return null;
    }

    @Override
    public List<CandidateDto> fetchAllCandidates() {
        return List.of();
    }

}
