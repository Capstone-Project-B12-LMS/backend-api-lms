package com.capstoneprojectb12.lms.backendapilms.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity(name = "student_quizzes")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class StudentQuiz {

    @ManyToOne
    @Column(length = 255)
    private List<Quiz> quizzes;

    @ManyToOne
    @Column(length = 255)
    private List<User> users;

    private boolean isDone;

    private int point;

    @Column(length = 255, nullable = false)
    private String createdBy;

    @Column(length = 255, nullable = false)
    private String deletedBy;

    @Column(length = 255, nullable = false)
    private String updatedBy;



}
