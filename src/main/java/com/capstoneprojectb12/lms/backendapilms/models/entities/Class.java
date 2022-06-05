package com.capstoneprojectb12.lms.backendapilms.models.entities;

import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;
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
public class Class extends BaseEntity{


    @Column(nullable = false)
    private String name;

    private String room;

    @Column(length = 10 ,nullable = false, unique = true)
    private String code;

    @Enumerated(value = EnumType.STRING)
    private ClassStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> users;



}
