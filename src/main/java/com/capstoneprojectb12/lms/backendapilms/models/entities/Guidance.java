package com.capstoneprojectb12.lms.backendapilms.models.entities;

import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.GuidanceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;


@Entity(name = "guidances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Guidance extends BaseEntity{



    private String topic;

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "class")
    private Class classes;

    @Enumerated(value = EnumType.STRING)
    private GuidanceStatus status;

}
