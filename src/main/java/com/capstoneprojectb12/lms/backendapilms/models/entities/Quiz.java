package com.capstoneprojectb12.lms.backendapilms.models.entities;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "quizzes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SQLDelete(sql = "UPDATE quizzes SET isDeleted = true WHERE id = ?")
@Setter
@SuperBuilder

public class Quiz extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private Assignment assignment;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000, nullable = false)
    private String question;

    @Column(length = 1000, nullable = false)
    private String answers;

}
