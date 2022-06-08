package com.capstoneprojectb12.lms.backendapilms.models.entities;

import javax.persistence.*;

import org.hibernate.annotations.SQLDelete;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "comments")
@Getter
@Setter
@SQLDelete(sql = "UPDATE comments SET is_deleted = true WHERE id = ?")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Comment extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "class")
    private Class classes;

    @ManyToOne
    private User user;

    @Column(length = 1000, nullable = false)
    private String content;

}
