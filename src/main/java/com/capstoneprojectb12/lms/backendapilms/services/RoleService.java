package com.capstoneprojectb12.lms.backendapilms.services;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Role;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.RoleRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;

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
        var role = this.findById(id);
        if (role.isPresent() && !role.get().getIsDeleted()) {
            this.roleRepository.deleteById(id);
            log.info("Success deleted");
            return true;
        }
        log.error("Data not found");
        return false;
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
        // TODO Auto-generated method stub
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

    @Override
    public PaginationResponse<List<Role>> toPaginationResponse(Page<Role> page) {
        var result = PaginationResponse.<List<Role>>builder()
                .data(page.getContent())
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalPage(page.getTotalPages())
                .totalSize(page.getTotalElements())
                .build();
        return result;
    }

    public List<Role> findByNames(String... names) {
        var roles = new ArrayList<Role>();
        for (var name : names) {
            var role = this.roleRepository.findByNameEqualsIgnoreCase(name);
            if (!role.isPresent()) {
                var roleNew = Role.builder()
                        .name(name)
                        .description("-")
                        .isDeleted(false)
                        .build();
                role = this.save(roleNew);
            }
            roles.add(role.get());
        }
        return roles;
    }

}
