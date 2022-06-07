package com.capstoneprojectb12.lms.backendapilms.models.entities;

import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Class extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String room;

    @Column(length = 10, nullable = false, unique = true)
    private String code;

    @Enumerated(value = EnumType.STRING)
    private ClassStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> users;

    @PrePersist
    public void onInsertClass() {
        this.code = UUID.randomUUID().toString().substring(0, 10);
        this.status = ClassStatus.ACTIVE;
    }
}
