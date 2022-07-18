package com.capstoneprojectb12.lms.backendapilms.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity(name = "comments")
@Getter
@Setter
@SQLDelete(sql = "UPDATE comments SET is_deleted = true WHERE id = ?")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Feedback extends BaseEntity {
	
	@ManyToOne
	@JoinColumn(name = "class")
	private Class classEntity;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	@Column(length = 1000, nullable = false)
	private String content;
	
}
