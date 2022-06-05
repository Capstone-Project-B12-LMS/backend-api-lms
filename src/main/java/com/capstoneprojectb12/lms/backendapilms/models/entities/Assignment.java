package com.capstoneprojectb12.lms.backendapilms.models.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity (name = "assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Assignment extends BaseEntity{


    @ManyToOne
    @JoinColumn(name = "class")
    private Class classes;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne
    private Topic topic;

    @Column(length = 1000)
    private String videoUri;

    @Column(length = 1000)
    private String fileUrl;

    private LocalDateTime deadline;

    @Column(length = 3, nullable = false)
    private int point;

    @ManyToOne
    private Category category;



}
