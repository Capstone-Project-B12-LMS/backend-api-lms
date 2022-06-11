package com.capstoneprojectb12.lms.backendapilms.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.classes.ClassUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Class;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ClassService implements BaseService<Class> {
    private final ClassRepository classRepository;

    @Override
    public Optional<Class> save(Class entity) {
        return Optional.ofNullable(this.classRepository.save(entity));
    }

    @Override
    public Optional<Class> update(Class entity) {
        return this.save(entity);
    }

    public Optional<Class> update(Class entity, ClassUpdate classUpdate) {
        entity.setName(classUpdate.getName());
        entity.setRoom(classUpdate.getRoom());
        entity.setStatus(classUpdate.getStatus());
        return this.save(entity);
    }

    @Override
    public boolean deleteById(String id) {
        var className = this.existsById(id);
        if (className) {
            this.classRepository.deleteById(id);
            log.info("Success delete class");
        }
        return className;
    }

    @Override
    public boolean existsById(String id) {
        return this.classRepository.existsById(id);
    }

    @Override
    public Optional<Class> findById(String id) {
        return this.classRepository.findById(id);
    }

    @Override
    public List<Class> findAll() {
        return this.classRepository.findAll();
    }

    @Override
    public List<Class> findAll(boolean showDeleted) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Class> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var classPage = this.classRepository.findAll(pageable);
        return classPage;
    }

    @Override
    public Page<Class> findAll(int page, int size, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        var classPage = this.classRepository.findAll(pageable);
        return classPage;
    }

    @Override
    public PaginationResponse<List<Class>> toPaginationResponse(Page<Class> page) {
        return PaginationResponse.<List<Class>>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalPage(page.getTotalPages())
                .totalSize(page.getTotalElements())
                .data(page.getContent())
                .build();
    }

    public Class toEntity(ClassNew classNew) {
        return Class.builder()
                .name(classNew.getName())
                .room(classNew.getRoom())
                .build();
    }
}
