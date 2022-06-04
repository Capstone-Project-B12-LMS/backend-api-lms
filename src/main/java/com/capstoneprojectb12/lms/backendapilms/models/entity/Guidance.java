package com.capstoneprojectb12.lms.backendapilms.models.entity;

import com.capstoneprojectb12.lms.backendapilms.models.entity.utils.ClassStatus;
import com.capstoneprojectb12.lms.backendapilms.models.entity.utils.GuidanceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity(name = "guidances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Guidance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(length = 255)
    private String topik;

    @Column(length = 1000, nullable = false)
    private String content;

    @ManyToOne
    @Column(length = 255)
    private List<Class> classes;

    @Enumerated(value = EnumType.STRING)
    private GuidanceStatus status;

    @Column(length = 255, nullable = false)
    private String createdBy;

    @Column(length = 255, nullable = false)
    private String deletedBy;

    @Column(length = 255, nullable = false)
    private String updatedBy;
}
