package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialNew;
import com.capstoneprojectb12.lms.backendapilms.models.dtos.material.MaterialUpdate;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Category;
import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.CategoryRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.ClassRepository;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.MaterialRepository;
import com.capstoneprojectb12.lms.backendapilms.services.mongodb.ActivityHistoryService;
import com.capstoneprojectb12.lms.backendapilms.utilities.DateUtils;
import com.capstoneprojectb12.lms.backendapilms.utilities.FinalVariable;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.ClassNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.DataNotFoundException;
import com.capstoneprojectb12.lms.backendapilms.utilities.gql.PaginationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete.deleted;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.*;
import static com.capstoneprojectb12.lms.backendapilms.utilities.histories.ActivityHistoryUtils.youAreSuccessfully;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MaterialService implements BaseService<Material, MaterialNew, MaterialUpdate> {
    private final MaterialRepository materialRepository;
    private final ClassRepository classRepository;
    private final CategoryRepository categoryRepository;
    private final ActivityHistoryService history;
    private final EntityManager entityManager;

    @Override
    public ResponseEntity<?> save(MaterialNew newEntity) {
        try {
            var material = this.toEntity(newEntity);
            material = this.materialRepository.save(material);

            history.save(youAreSuccessfully(String.format("created Material \"%s\"", newEntity.getTitle())));

            return ok(material);
        } catch (ClassNotFoundException e) {
            log.warn(FinalVariable.DATA_NOT_FOUND);
            return bad(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return err(e);
        }
    }

    @Override
    public ResponseEntity<?> update(String entityId, MaterialUpdate updateEntity) {
        try {
            var temp = this.toEntity(updateEntity);
            var material = this.materialRepository.findById(entityId).orElseThrow(DataNotFoundException::new);

            material.setClassEntity(temp.getClassEntity());
            material.setContent(temp.getContent());
            material.setPoint(temp.getPoint());
            material.setDeadline(temp.getDeadline());
            material.setTitle(temp.getTitle());
            material.setVideoUrl(temp.getVideoUrl()); // TODO: create file service first
            material.setFileUrl(temp.getFileUrl()); // TODO: create file service first
            material.setTopic(temp.getTopic()); // TODO: create topic repo/service first
            material.setCategory(temp.getCategory());

            material = this.materialRepository.save(material);

            history.save(youAreSuccessfully(String.format("updated Material \"%s\" ", updateEntity.getTitle())));

            return ok(material);
        } catch (DataNotFoundException e) {
            log.error(e.getMessage());
            return bad(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return err(e);
        }
    }

    @Override
    public ResponseEntity<?> deleteById(String id) {
        try {
            var material = this.materialRepository.findById(id);
            if (material.isEmpty()) {
                log.warn(FinalVariable.DATA_NOT_FOUND);
                return bad(FinalVariable.DATA_NOT_FOUND);
            }
            this.classRepository.deleteById(id);
            log.info(FinalVariable.DELETE_SUCCESS);

            history.save(youAreSuccessfully(String.format("deleted Material \"%s\"", this.materialRepository.findById(id).get().getTitle())));

            return ok(deleted(material.get()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return err(e);
        }
    }

    @Override
    public ResponseEntity<?> findById(String id) {
        return this.findById(id, false);
    }

    public ResponseEntity<?> findById(String id, boolean showDeleted) {
        try {
            var session = entityManager.unwrap(Session.class);
            var filter = session.enableFilter("showDeleted");

            filter.setParameter("isDeleted", showDeleted);
            var material = this.materialRepository.findById(id).orElseThrow(DataNotFoundException::new);
            session.disableFilter("showDeleted");

            return ok(material);
        } catch (DataNotFoundException e) {
            log.error(e.getMessage());
            return bad(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return err(e);
        }
    }

    @Override
    public ResponseEntity<?> findAll() {
        return this.findAll(false);
    }

    @Override
    public ResponseEntity<?> findAll(boolean showDeleted) {
        try {
            var session = entityManager.unwrap(Session.class);
            var filter = session.enableFilter("showDeleted");

            filter.setParameter("isDeleted", showDeleted);
            var materials = this.materialRepository.findAll();
            session.disableFilter("showDeleted");

            return ok(materials);
        } catch (Exception e) {
            log.error(e.getMessage());
            return err(e);
        }
    }

    @Override
    public ResponseEntity<?> findAll(int page, int size) {
        return this.findAll(page, size, Sort.unsorted());
    }

    @Override
    public ResponseEntity<?> findAll(int page, int size, Sort sort) {
        return this.findAll(page, size, sort, false);
    }

    public ResponseEntity<?> findAll(int page, int size, Sort sort, boolean showDeleted) {
        try {
            var request = PageRequest.of(page, size, sort);

            var session = entityManager.unwrap(Session.class);
            var filter = session.enableFilter("showDeleted");

            filter.setParameter("isDeleted", showDeleted);
            var materials = this.materialRepository.findAll(request);
            session.disableFilter("showDeleted");

            var responsePage = this.toPaginationResponse(materials);
            return ok(materials);
        } catch (Exception e) {
            log.error(e.getMessage());
            return err(e);
        }
    }

    @Override
    public PaginationResponse<List<Material>> toPaginationResponse(Page<Material> page) {
        return PaginationResponse.<List<Material>>builder()
                .page(page.getNumber())
                .size(page.getSize())
                .totalPage(page.getTotalPages())
                .totalSize(page.getTotalElements())
                .data(page.getContent())
                .build();
    }

    @Override
    public Material toEntity(MaterialNew materialNew) {
        return Material.builder()
                .classEntity(this.classRepository.findById(materialNew.getClassId()).orElseThrow(ClassNotFoundException::new))
//				.category(materialNew.getVideo()) // TODO: create when category not found
//				.topic(materialNew.getTopicId()) // TODO: find topic by id or maybe create new topic if not exists
//				.fileUrl() // TODO: save file first
                .category(this.categoryRepository.findByNameEqualsIgnoreCase(materialNew.getCategory()).orElseGet(() -> {
                    var category = Category.builder()
                            .name(materialNew.getCategory())
                            .description("-")
                            .build();
                    return this.categoryRepository.save(category);
                })) // TODO: create when category not found
//				.topic(materialNew.getTopicId()) // TODO: find topic by id or maybe create new topic if not exists
                .fileUrl(materialNew.getFile()) // TODO: save file first
                .videoUrl(materialNew.getVideo()) // TODO: save file first
                .content(materialNew.getContent())
                .point(materialNew.getPoint())
                .deadline(DateUtils.parse(materialNew.getDeadline()))
                .title(materialNew.getTitle())
                .build();
    }

    public ResponseEntity<?> findAllByClassId(String classId) {
        return this.findAllByClassId(classId, false);
    }

    public ResponseEntity<?> findAllByClassId(String classId, boolean showDeleted) {
        try {
            var session = entityManager.unwrap(Session.class);
            var filter = session.enableFilter("showDeleted");
            filter.setParameter("isDeleted", showDeleted);
            var materials = this.materialRepository.findByClassEntityIdOrderByCreatedAtAsc(classId).orElseThrow(ClassNotFoundException::new);
            session.disableFilter("showDeleted");
            return ok(materials);
        } catch (ClassNotFoundException e) {
            log.warn(e.getMessage());
            return bad(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return err(e);
        }
    }
}
