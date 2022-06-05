package com.capstoneprojectb12.lms.backendapilms.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Category extends BaseEntity {

    @Column(unique = true)
    private String name;

    @Column(length = 1000)
    private String description;
}
