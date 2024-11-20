package com.omer.candidate.entity;

import com.omer.candidate.enums.MilitaryStatus;
import com.omer.candidate.enums.Notice_Period;
import com.omer.candidate.enums.PositionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class Candidate extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private PositionType positionType;
    @Enumerated(EnumType.STRING)
    private MilitaryStatus militaryStatus;
    @Enumerated(EnumType.STRING)
    private Notice_Period noticePeriod;
    private String email;
    private String phone;
    private String cvPath;
}
