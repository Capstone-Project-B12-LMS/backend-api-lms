package com.capstoneprojectb12.lms.backendapilms.models.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity (name = "assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @Column(length = 255)
    private List<Class> classes;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @Column(length = 255)
    private List<Topic> topics;

    @Column(length = 1000)
    private String videoUri;

    @Column(length = 1000)
    private String fileUri;

    private LocalDateTime deadline;

    @Column(length = 3, nullable = false)
    private int point;

    @ManyToOne
    @Column(length = 255)
    private List<Category> categories;

    @Column(length = 255)
    private String createdBy;


}
