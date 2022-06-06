package com.capstoneprojectb12.lms.backendapilms.models.entities;

import javax.persistence.*;

import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.GuidanceStatus;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "guidances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Guidance extends BaseEntity {

    private String topic;

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "class")
    private Class classes;

    @Enumerated(value = EnumType.STRING)
    private GuidanceStatus status;

}