package com.capstoneprojectb12.lms.backendapilms.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @Column(length = 255)
    private List<Class> classes;

    @ManyToOne
    @Column(length = 255)
    private List<User> users;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column(length = 255, nullable = false)
    private String createdBy;

    @Column(length = 255, nullable = false)
    private String deletedBy;

    @Column(length = 255, nullable = false)
    private String updatedBy;
}
