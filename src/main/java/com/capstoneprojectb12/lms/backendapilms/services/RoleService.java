package com.capstoneprojectb12.lms.backendapilms.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService implements BaseService<Role> {
    @Override
    public Optional<Role> save(Role entity) {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public Optional<Role> update(Role entity) {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public boolean deleteById(String id) {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public Optional<Role> findById() {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public List<Role> findAll() {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public List<Role> findAll(boolean showDeleted) {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public Page<Role> findAll(int page, int size) {
        throw new RuntimeException("Method not implemented");
    }

    @Override
    public Page<Role> findAll(int page, int size, Sort sort) {
        throw new RuntimeException("Method not implemented");
    }

}
