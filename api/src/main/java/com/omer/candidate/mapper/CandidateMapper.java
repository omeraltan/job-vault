package com.omer.candidate.mapper;

import com.omer.candidate.dto.CandidateDto;
import com.omer.candidate.entity.Candidate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CandidateMapper {

    CandidateMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CandidateMapper.class);

    // Entity to DTO
    CandidateDto toCandidateDto(Candidate candidate);

    // DTO to Entity
    @Mapping(target = "id", ignore = true)
    Candidate toCandidate(CandidateDto candidateDto);

}
