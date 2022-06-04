package com.capstoneprojectb12.lms.backendapilms.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity(name = "topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(length =  255 , nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String createdBy;

    @Column(length = 255, nullable = false)
    private String deletedBy;

    @Column(length = 255, nullable = false)
    private String updatedBy;
}
