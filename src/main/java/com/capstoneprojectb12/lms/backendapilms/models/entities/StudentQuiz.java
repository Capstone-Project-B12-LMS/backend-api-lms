package com.capstoneprojectb12.lms.backendapilms.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "student_quizzes")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class StudentQuiz extends BaseEntity{

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private boolean isDone;

    @Column(length = 3, nullable = false)
    private int point;


}
