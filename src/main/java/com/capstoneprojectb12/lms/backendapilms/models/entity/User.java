package com.capstoneprojectb12.lms.backendapilms.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity (name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(length =  255 , nullable = false)
    private String fullName;

    @Column(length = 255 , nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String passwords;
}
