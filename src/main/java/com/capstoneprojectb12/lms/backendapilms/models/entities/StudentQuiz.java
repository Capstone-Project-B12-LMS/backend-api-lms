package com.capstoneprojectb12.lms.backendapilms.models.entities;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "student_quizzes")
@AllArgsConstructor
@SQLDelete(sql = "UPDATE student_quizzes SET is_deleted = true WHERE id = ?")
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class StudentQuiz extends BaseEntity {

    @ManyToOne
    private Quiz quiz;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private boolean isDone;

    @Column(length = 3, nullable = false)
    private int point;

}
