package com.capstoneprojectb12.lms.backendapilms.models.dtos.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserLogin {
    private String email;
    private String password;
}
