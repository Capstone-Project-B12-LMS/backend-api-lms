package com.capstoneprojectb12.lms.backendapilms.models.entity;

import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity(name = "quizzes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder

public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @Column(length = 255)
    private List<Assignment> assignments;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 1000, nullable = false)
    private String question;

    @Column(length = 1000, nullable = false)
    private String answers;

    @Column(length = 255, nullable = false)
    private String createdBy;

    @Column(length = 255, nullable = false)
    private String deletedBy;

    @Column(length = 255, nullable = false)
    private String updatedBy;


}
