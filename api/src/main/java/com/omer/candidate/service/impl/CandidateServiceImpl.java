package com.omer.candidate.service.impl;

import com.omer.candidate.dto.CandidateDto;
import com.omer.candidate.entity.Candidate;
import com.omer.candidate.exception.*;
import com.omer.candidate.exception.IllegalArgumentException;
import com.omer.candidate.mapper.CandidateMapper;
import com.omer.candidate.repository.CandidateRepository;
import com.omer.candidate.service.ICandidateService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements ICandidateService {

    private CandidateRepository candidateRepository;
    private CandidateMapper candidateMapper;

    @Override
    @Transactional
    public void createCandidate(CandidateDto candidateDto) {

        Optional<Candidate> emailCandidate = candidateRepository.findByEmail(candidateDto.getEmail());
        if (emailCandidate.isPresent()) {
            throw new EmailAlreadyExistException("Email already exists : " + candidateDto.getEmail());
        }

        Optional<Candidate> phoneCandidate = candidateRepository.findByPhone(candidateDto.getPhone());
        if (phoneCandidate.isPresent()) {
            throw new PhoneAlreadyExistException("Phone already exists : " + candidateDto.getPhone());
        }

        if (candidateDto.getCv() == null || candidateDto.getCv().isEmpty()) {
            throw new IllegalArgumentException("Cv can not be null or empty : " + candidateDto.getCv());
        }

        String originalFilename = candidateDto.getCv().getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("Only PDF files are allowed : " + originalFilename);
        }

        try {
            String uploadDir = System.getProperty("user.dir") + "/resumes";
            String fileName = candidateDto.getFirstName() + "_" + candidateDto.getLastName() + "_" + System.currentTimeMillis() + ".pdf";
            Path filePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, candidateDto.getCv().getBytes());
            Candidate candidate = candidateMapper.toCandidate(candidateDto);
            candidate.setCvPath(filePath.toString());
            candidateRepository.save(candidate);
        } catch (IOException e) {
            throw new FailedCvException("Failed to save CV file : " + candidateDto.getCv() + " " + e);
        }
    }

    @Override
    public void updateCandidate(CandidateDto candidateDto) {

    }

    @Override
    @Transactional
    public boolean  deleteCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Candidate not found with id : " + id)
        );
        String cvPath = candidate.getCvPath();
        if (cvPath != null && !cvPath.isEmpty()) {
            try {
                Path filePath = Paths.get(cvPath);
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete CV file: " + cvPath, e);
            }
        }
        candidateRepository.delete(candidate);
        return true;
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
