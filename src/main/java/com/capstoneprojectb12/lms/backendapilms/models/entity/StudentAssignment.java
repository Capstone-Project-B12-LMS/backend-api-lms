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

@Entity(name = "sudent_assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StudentAssignment {
    @ManyToOne
    @Column(length = 255)
    private List<User>users;

    @ManyToOne
    @Column(length = 255)
    private List<Assignment>assignments;

    private boolean isDone;

    @Column(length = 1000,nullable = false)
    private String fileUrl;

    @Column(length = 255, nullable = false)
    private String createdBy;

    @Column(length = 255, nullable = false)
    private String deletedBy;

    @Column(length = 255, nullable = false)
    private String updatedBy;
}
