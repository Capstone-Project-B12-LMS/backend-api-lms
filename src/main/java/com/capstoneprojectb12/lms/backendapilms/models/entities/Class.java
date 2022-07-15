package com.capstoneprojectb12.lms.backendapilms.models.entities;

import com.capstoneprojectb12.lms.backendapilms.models.entities.utils.ClassStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity()
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE classes SET is_deleted = true WHERE id = ?")
@SuperBuilder
public class Class extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String room;
    private String reportUrl;

    @Column(length = 10, nullable = false, unique = true)
    private String code;

    @Enumerated(value = EnumType.STRING)
    private ClassStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();

    @PrePersist
    public void onInsertClass() {
        this.code = UUID.randomUUID()
                .toString()
                .substring(0, 10)
                .replaceAll("-", String.valueOf(new Random().nextInt(9)));
        this.status = ClassStatus.ACTIVE;
    }
}
