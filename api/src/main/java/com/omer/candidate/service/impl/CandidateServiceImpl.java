package com.omer.candidate.service.impl;

import com.omer.candidate.dto.CandidateDto;
import com.omer.candidate.entity.Candidate;
import com.omer.candidate.exception.*;
import com.omer.candidate.exception.IllegalArgumentException;
import com.omer.candidate.mapper.CandidateMapper;
import com.omer.candidate.repository.CandidateRepository;
import com.omer.candidate.service.ICandidateService;
import com.omer.candidate.specification.CandidateSpecification;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements ICandidateService {

    private static final Logger logger = LoggerFactory.getLogger(CandidateServiceImpl.class);
    private CandidateRepository candidateRepository;
    private CandidateMapper candidateMapper;

    @Override
    @Transactional
    public void createCandidate(CandidateDto candidateDto) {

        logger.info("Attempting to create candidate with email: {}", candidateDto.getEmail());

        Optional<Candidate> emailCandidate = candidateRepository.findByEmail(candidateDto.getEmail());
        if (emailCandidate.isPresent()) {
            logger.error("Email already exists for new create: {}", candidateDto.getEmail());
            throw new EmailAlreadyExistException("Email already exists for new create: " + candidateDto.getEmail());
        }

        Optional<Candidate> phoneCandidate = candidateRepository.findByPhone(candidateDto.getPhone());
        if (phoneCandidate.isPresent()) {
            logger.error("Phone already exists for new create: {}", candidateDto.getPhone());
            throw new PhoneAlreadyExistException("Phone already exists fro new create : " + candidateDto.getPhone());
        }

        if (candidateDto.getCv() == null || candidateDto.getCv().isEmpty()) {
            logger.error("CV cannot be null or empty for new create: {}", candidateDto.getCv());
            throw new IllegalArgumentException("Cv can not be null or empty : " + candidateDto.getCv());
        }

        String originalFilename = candidateDto.getCv().getOriginalFilename();
        if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".pdf")) {
            logger.error("Only PDF files are allowed: {}", originalFilename);
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
            logger.info("Candidate created successfully with ID: {}", candidate.getId());
        } catch (IOException e) {
            logger.error("Error while saving CV file: {}", e.getMessage());
            throw new FailedCvException("Failed to save CV file : " + candidateDto.getCv() + " " + e);
        }
    }

    @Override
    @Transactional
    public void updateCandidate(Long id, CandidateDto candidateDto) {
        logger.info("Attempting to update candidate with id: {}", id);
        Candidate existingCandidate = candidateRepository.findById(id).orElseThrow(() -> {
                logger.error("Candidate not found with id: {}", id);
                return new ResourceNotFoundException("Candidate not found with id : " + id);
            }
        );

        Optional<Candidate> emailCandidate = candidateRepository.findByEmail(candidateDto.getEmail());
        if (emailCandidate.isPresent() && !emailCandidate.get().getId().equals(id)) {
            logger.error("Email already exists: {}", candidateDto.getEmail());
            throw new EmailAlreadyExistException("Email already exists: " + candidateDto.getEmail());
        }

        Optional<Candidate> phoneCandidate = candidateRepository.findByPhone(candidateDto.getPhone());
        if (phoneCandidate.isPresent() && !phoneCandidate.get().getId().equals(id)) {
            logger.error("Phone number already exists: {}", candidateDto.getPhone());
            throw new PhoneAlreadyExistException("Phone number already exists: " + candidateDto.getPhone());
        }

        if (candidateDto.getCv() != null && !candidateDto.getCv().isEmpty()) {
            try {
                if (existingCandidate.getCvPath() != null) {
                    Path oldFilePath = Paths.get(existingCandidate.getCvPath());
                    Files.deleteIfExists(oldFilePath);
                }
                String uploadDir = System.getProperty("user.dir") + "/resumes";
                String fileName = candidateDto.getFirstName() + "_" + candidateDto.getLastName() + "_" + System.currentTimeMillis() + ".pdf";
                Path newFilePath = Paths.get(uploadDir, fileName);
                Files.createDirectories(newFilePath.getParent());
                Files.write(newFilePath, candidateDto.getCv().getBytes());
                existingCandidate.setCvPath(newFilePath.toString());
                logger.info("Successfully updated CV file for candidate: {}", id);
            } catch (IOException e) {
                logger.error("Failed to update CV file for candidate: {}. Error: {}", id, e.getMessage());
                throw new RuntimeException("Failed to update CV file", e);
            }
        }
        existingCandidate.setFirstName(candidateDto.getFirstName());
        existingCandidate.setLastName(candidateDto.getLastName());
        existingCandidate.setEmail(candidateDto.getEmail());
        existingCandidate.setPhone(candidateDto.getPhone());
        candidateRepository.save(existingCandidate);
        logger.info("Successfully updated candidate details with id: {}", id);
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
        logger.info("Fetching candidate with ID: {}", id);
        Candidate candidate = candidateRepository.findById(id).orElseThrow(() -> {
            logger.error("Candidate not found with ID: {}", id);
            return new ResourceNotFoundException("Candidate not found with id : " + id);
        });
        logger.debug("Candidate found: {}", candidate);
        return candidateMapper.toCandidateDto(candidate);
    }

    @Override
    public List<CandidateDto> fetchAllCandidates() {
        logger.info("Fetching all candidates from the database");
        List<Candidate> candidates =  candidateRepository.findAll();
        if (candidates.isEmpty()) {
            logger.error("No candidates found");
            throw new ResourceNotFoundException("No candidates found");
        }
        logger.info("Successfully fetched {} candidates", candidates.size());
        return candidates.stream().map(candidateMapper::toCandidateDto).collect(Collectors.toList());
    }

    @Override
    public List<CandidateDto> fetchCandidatesWithFilters(String positionType, String militaryStatus, String noticePeriod) {
        logger.info("Fetching candidates with filters - PositionType: {}, MilitaryStatus: {}, NoticePeriod: {}", positionType, militaryStatus, noticePeriod);
        Specification<Candidate> spec = Specification.where(CandidateSpecification.hasPositionType(positionType))
            .and(CandidateSpecification.hasMilitaryStatus(militaryStatus))
            .and(CandidateSpecification.hasNoticePeriod(noticePeriod));
        List<Candidate> candidates = candidateRepository.findAll(spec);
        if(candidates.isEmpty()){
            throw new ResourceNotFoundException("No candidates found matching the provided filters");
        }
        return candidates.stream().map(candidateMapper::toCandidateDto).collect(Collectors.toList());
    }


}
