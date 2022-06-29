package com.capstoneprojectb12.lms.backendapilms.models.entities;

import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.GuidanceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity(name = "guidances")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE guidances SET is_deleted = true WHERE id = ?")
@SuperBuilder
public class Guidance extends BaseEntity {
	
	private String topic;
	
	@Column(length = 1000, nullable = false)
	private String content;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "class")
	private Class className;
	
	@Enumerated(value = EnumType.STRING)
	private GuidanceStatus status;
	
}
