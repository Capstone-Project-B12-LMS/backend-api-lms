package com.capstoneprojectb12.lms.backendapilms.models.entities;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "sudent_assignments")
@Getter
@SQLDelete(sql = "UPDATE sudent_assignments SET is_deleted = true WHERE id = ?")
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
