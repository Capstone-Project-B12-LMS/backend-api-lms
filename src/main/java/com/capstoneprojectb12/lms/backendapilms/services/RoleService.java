package com.capstoneprojectb12.lms.backendapilms.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoleService implements BaseService<Role> {
    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> save(Role entity) {
        return Optional.of(Optional.of(this.roleRepository.save(entity))).orElse(Optional.empty());
    }

    @Override
    public Optional<Role> update(Role entity) {
        return this.save(entity);
    }

    @Override
    public boolean deleteById(String id) {
        var isExists = this.existsById(id);
        if (isExists) {
            this.roleRepository.deleteById(id);
        }
        return isExists;
    }

    @Override
    public Optional<Role> findById(String id) {
        return this.roleRepository.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return this.roleRepository.findAll();
    }

    @Override
    public List<Role> findAll(boolean showDeleted) {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public Page<Role> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var roles = this.roleRepository.findAll(pageable);
        return roles;
    }

    @Override
    public Page<Role> findAll(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        var roles = this.roleRepository.findAll(pageable);
        return roles;
    }

    @Override
    public boolean existsById(String id) {
        return this.roleRepository.existsById(id);
    }

}
