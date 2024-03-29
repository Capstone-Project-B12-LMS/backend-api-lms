package com.capstoneprojectb12.lms.backendapilms.models.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

@Entity(name = "users")
@Getter
@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id = ?")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity implements UserDetails {
	@Column(nullable = false)
	private String fullName;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	@Default
	private String telepon = "";
	
	private boolean enable = false;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Column(nullable = false)
	@Builder.Default
	private List<Role> roles = new ArrayList<>();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles
				.stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName().toUpperCase()))
				.collect(Collectors.toList());
	}
	
	@Override
	public String getUsername() {
		return this.email;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return this.isEnabled();
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return this.isEnabled();
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return this.isEnabled();
	}
	
	@Override
	public boolean isEnabled() {
		return this.enable;
	}
}
