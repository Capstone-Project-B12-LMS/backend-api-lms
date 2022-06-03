package com.capstoneprojectb12.lms.backendapilms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.*;

@Entity(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description = "-";
}
