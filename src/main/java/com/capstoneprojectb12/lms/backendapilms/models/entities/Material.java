package com.capstoneprojectb12.lms.backendapilms.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity(name = "materials")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE materials SET is_deleted = true WHERE id = ?")
@SuperBuilder
public class Material extends BaseEntity {

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
