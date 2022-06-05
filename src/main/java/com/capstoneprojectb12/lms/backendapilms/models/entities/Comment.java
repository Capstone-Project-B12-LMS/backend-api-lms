package com.capstoneprojectb12.lms.backendapilms.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Entity(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Comment extends BaseEntity{


    @ManyToOne
    @JoinColumn(name = "class")
    private Class classes;

    @ManyToOne
    private User user;

    @Column(length = 1000, nullable = false)
    private String content;

    }
