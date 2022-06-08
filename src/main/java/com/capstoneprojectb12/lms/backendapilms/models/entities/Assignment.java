package com.capstoneprojectb12.lms.backendapilms.models.entities;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE assignments SET is_deleted = true WHERE id = ?")
@SuperBuilder
public class Assignment extends BaseEntity {

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
