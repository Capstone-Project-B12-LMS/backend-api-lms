package com.capstoneprojectb12.lms.backendapilms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.*;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Column(nullable = false, length = 255)
    private String fullName;

    @Column(nullable = false, length = 255, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;
}
