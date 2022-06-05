package com.capstoneprojectb12.lms.backendapilms.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity(name = "sudent_assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StudentAssignment extends BaseEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Assignment assignment;

    private boolean isDone;

    @Column(length = 1000)
    private String fileUrl;

}
