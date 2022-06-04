package com.capstoneprojectb12.lms.backendapilms.models.entity;

import com.capstoneprojectb12.lms.backendapilms.models.entity.utils.ClassStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity (name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255)
    private String room;

    @Column(length = 10 ,nullable = false, unique = true)
    private String code;

    @Enumerated(value = EnumType.STRING)
    private ClassStatus status;

    @ManyToMany
    private List<User>users;

    @Column(length = 255, nullable = false)
    private String createdBy;


}
