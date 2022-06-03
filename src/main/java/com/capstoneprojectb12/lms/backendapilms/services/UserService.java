package com.capstoneprojectb12.lms.backendapilms.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.user.UserNew;
import com.capstoneprojectb12.lms.backendapilms.models.entities.User;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.UserRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements BaseService<User>, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByEmailEqualsIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public Optional<User> save(User entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        var savedUser = this.userRepository.save(entity);
        return Optional.of(Optional.of(savedUser)).orElse(Optional.empty());
    }

    @Override
    public Optional<User> update(User entity) {
        var updatedUser = this.userRepository.save(entity);
        return Optional.of(Optional.of(updatedUser)).orElse(Optional.empty());
    }

    @Override
    public boolean deleteById(String id) {
        var user = this.findById(id);
        if (user.isPresent() && !user.get().getIsDeleted()) {
            this.userRepository.deleteById(id);
            log.info("Success deleted");
            return true;
        }
        log.error("Data not found");
        return false;
    }

    @Override
    public boolean existsById(String id) {
        return this.userRepository.existsById(id);
    }

    @Override
    public Optional<User> findById(String id) {
        return this.userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public List<User> findAll(boolean showDeleted) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<User> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var users = this.userRepository.findAll(pageable);
        return users;
    }

    @Override
    public Page<User> findAll(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        var users = this.userRepository.findAll(pageable);
        return users;
    }

    @Override
    public PaginationResponse<List<User>> toPaginationResponse(Page<User> page) {
        return PaginationResponse.<List<User>>builder()
                .data(page.getContent())
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalPage(page.getTotalPages())
                .totalSize(page.getTotalElements())
                .build();
    }

    public User toEntity(UserNew userNew) {
        return User.builder()
                .fullName(userNew.getFullName())
                .email(userNew.getEmail())
                .password(userNew.getPassword())
                .roles(this.roleService
                        .findByNames(userNew
                                .getRoles()
                                .toArray(new String[userNew.getRoles().size()])))
                .build();
    }
}
