package com.capstoneprojectb12.lms.backendapilms.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MaterialService implements BaseService<Material> {
    @Override
    public Optional<Material> save(Material entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Material> update(Material entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deleteById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Optional<Material> findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Material> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Material> findAll(boolean showDeleted) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Material> findAll(int page, int size) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Material> findAll(int page, int size, Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PaginationResponse<List<Material>> toPaginationResponse(Page<Material> page) {
        // TODO Auto-generated method stub
        return null;
    }

}
