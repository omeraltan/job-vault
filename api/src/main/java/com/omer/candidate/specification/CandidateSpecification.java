package com.omer.candidate.specification;

import com.omer.candidate.entity.Candidate;
import org.springframework.data.jpa.domain.Specification;

public class CandidateSpecification {

    public static Specification<Candidate> hasPositionType(String positionType){
        return (root, query, criteriaBuilder) ->
            positionType == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("positionType"), positionType);
    }

    public static Specification<Candidate> hasMilitaryStatus(String militaryStatus){
        return (root, query, criteriaBuilder) ->
            militaryStatus == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("militaryStatus"), militaryStatus);
    }

    public static Specification<Candidate> hasNoticePeriod(String noticePeriod) {
        return (root, query, criteriaBuilder) ->
            noticePeriod == null ? criteriaBuilder.conjunction() :
                criteriaBuilder.equal(root.get("noticePeriod"), noticePeriod);
    }

}
