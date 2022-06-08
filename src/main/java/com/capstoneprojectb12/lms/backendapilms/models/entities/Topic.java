package com.capstoneprojectb12.lms.backendapilms.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.SQLDelete;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity(name = "topics")
@Getter
@Setter
@SQLDelete(sql = "UPDATE topics SET is_deleted = true WHERE id = ?")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Topic extends BaseEntity {

    @Column(nullable = false)
    private String name;

}
