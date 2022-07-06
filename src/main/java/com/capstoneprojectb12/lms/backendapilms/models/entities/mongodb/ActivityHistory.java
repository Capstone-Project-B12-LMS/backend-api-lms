package com.capstoneprojectb12.lms.backendapilms.models.entities.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;

import com.capstoneprojectb12.lms.backendapilms.models.entities.BaseEntity;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Document(value = "activity_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ActivityHistory extends BaseEntity {
    private User user;
    private String content;
}
