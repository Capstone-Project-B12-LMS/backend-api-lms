package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete;
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
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.AnyException;
import com.capstoneprojectb12.lms.backendapilms.utilities.exceptions.ClassNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.capstoneprojectb12.lms.backendapilms.models.dtos.base.ResponseDelete.deleted;
import static com.capstoneprojectb12.lms.backendapilms.services.CategoryServiceTest.category;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;
import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.getResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest(classes = {MaterialService.class})
@ExtendWith(MockitoExtension.class)
@Tag(value = "materialServiceTest")
public class MaterialServiceTest {
    public static final MaterialNew materialNew = MaterialNew.builder()
            .classId("id")
            .category("material category")
            .content("material content")
            .deadline("14/06/2022 22:34:50")
            .point(100)
            .file("url") // TODO: create file service first
            .video("url") // TODO: create file service first
            .title("material title")
            .topicId("topicId")
            .build();
    public static final Material material = Material.builder()
            .id("id")
            .classEntity(ClassServiceTest.classEntity)
            .title("material title")
            .content("material content")
            .topic(null)
            .videoUrl("url")
            .fileUrl("url")
            .deadline(DateUtils.parse(materialNew.getDeadline()))
            .point(100)
            .category(null)
            .build();

    private static final MaterialUpdate materialUpdate = MaterialUpdate.builder()
            .category("category name")
            .classId("id")
            .content("material content")
            .deadline("31/12/2022 23:54:59")
            .file("updated url")
            .video("updated url")
            .topicId(null)
            .title("updated title")
            .point(100)
            .build();
    @Autowired
    private MaterialService materialService;
    @MockBean
    private MaterialRepository materialRepository;
    @MockBean
    private ClassRepository classRepository;
    @MockBean
    private ActivityHistoryService activityHistoryService;

    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private EntityManager entityManager;

    @Test
    public void testNotNull() {
        assertNotNull(materialRepository);
    }

    @Test
    public void testSave() {
//        success
        when(this.materialRepository.save(any(Material.class))).thenReturn(material);
        when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
        var res = this.materialService.save(materialNew);
        var api = getResponse(res);
        var materialData = (Material) api.getData();

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertInstanceOf(Material.class, api.getData());
        assertNotNull(api.getData());
        assertNull(api.getErrors());
        assertTrue(api.isStatus());
        assertEquals(material.getId(), materialData.getId());
        assertEquals(material.getFileUrl(), materialData.getFileUrl());
        assertEquals(material.getVideoUrl(), materialData.getVideoUrl());
        reset(this.classRepository, this.materialRepository);

//		class with {classId} nto found
        when(this.materialRepository.save(any(Material.class))).thenReturn(material);
        when(this.classRepository.findById(anyString())).thenReturn(Optional.empty());
        res = this.materialService.save(materialNew);
        api = getResponse(res);
        materialData = (Material) api.getData();

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertNull(api.getData());
        assertNotNull(api.getErrors());
        assertFalse(api.isStatus());
        assertInstanceOf(HashMap.class, api.getErrors());
        assertNotNull(((HashMap<String, Object>) api.getErrors()).get("message"));
        reset(this.classRepository, this.materialRepository);

//		any exceptions
        when(this.materialRepository.save(any(Material.class))).thenThrow(AnyException.class);
        when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
        res = this.materialService.save(materialNew);
        api = getResponse(res);
        materialData = (Material) api.getData();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertNull(api.getData());
        assertNotNull(api.getErrors());
        assertFalse(api.isStatus());
        assertInstanceOf(HashMap.class, api.getErrors());
        assertNotNull(((HashMap<String, Object>) api.getErrors()).get("message"));
        reset(this.classRepository, this.materialRepository);
    }

    @Test
    public void testToEntity() {
//		success
        when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
        when(this.categoryRepository.findByNameEqualsIgnoreCase(anyString())).thenReturn(Optional.of(category));
        var result = this.materialService.toEntity(materialNew);
        assertNotNull(result);
        assertEquals(materialNew.getClassId(), result.getClassEntity().getId());
//		assertEquals(materialNew.getCategory(), result.getCategory()); // TODO: create category repo/service first
//		assertEquals(materialNew.getTopicId(), result.getTopic().getId()); // TODO: create topic repo/service first
//		assertEquals(materialNew.getFile().getOriginalFilename(), result.getFileUrl()); // TODO: create file service first
//		assertEquals(materialNew.getVideo().getOriginalFilename(), result.getVideoUrl()); // TODO: create file service first

        assertEquals(category.getName(), result.getCategory().getName()); // TODO: create category repo/service first
//		assertEquals(materialNew.getTopicId(), result.getTopic().getId()); // TODO: create topic repo/service first
//		assertEquals(materialNew.getFile().getOriginalFilename(), result.getFileUrl()); // TODO: create file service first
//		assertEquals(materialNew.getVideo().getOriginalFilename(), result.getVideoUrl()); // TODO: create file service first
        assertEquals(materialNew.getContent(), result.getContent());
        assertEquals(materialNew.getVideo(), result.getVideoUrl());
        assertEquals(materialNew.getFile(), result.getFileUrl());
        assertEquals(DateUtils.parse(materialNew.getDeadline()), result.getDeadline());
        assertEquals(materialNew.getPoint(), result.getPoint());
        assertEquals(materialNew.getTitle(), result.getTitle());

//		test exception when parse string to date
//		var materialFailed = MaterialNew.builder()
//				.deadline("hd930")
//				.classId("id")
//				.build();
        var tempDeadline = materialNew.getDeadline();
        materialNew.setDeadline("anyy");
        result = this.materialService.toEntity(materialNew);
        materialNew.setDeadline(tempDeadline);
        assertNull(result.getDeadline());
        reset(this.classRepository);

//		failed
        when(this.classRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ClassNotFoundException.class, () -> this.materialService.toEntity(materialNew));
        reset(this.classRepository);

//		create new category if not exists
        when(this.categoryRepository.findByNameEqualsIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
        when(this.categoryRepository.save(any(Category.class))).thenReturn(Category.builder().id("id").name(materialNew.getCategory()).build());
        var material = this.materialService.toEntity(materialNew);

        assertNotNull(material);
        assertEquals(materialNew.getClassId(), material.getClassEntity().getId());
        assertEquals(materialNew.getPoint(), material.getPoint());
        assertEquals(materialNew.getCategory(), material.getCategory().getName());
        assertEquals(materialNew.getContent(), material.getContent());
        assertEquals(materialNew.getDeadline(), new SimpleDateFormat(FinalVariable.DATE_FORMAT).format(material.getDeadline()));
        assertEquals(materialNew.getFile(), material.getFileUrl());
        assertEquals(materialNew.getTitle(), material.getTitle());
        assertEquals(materialNew.getVideo(), material.getVideoUrl());
    }

    @Test
    public void testUpdate() {
//		success
        when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
        when(this.categoryRepository.findByNameEqualsIgnoreCase(anyString())).thenReturn(Optional.of(category));
        when(this.materialRepository.findById(anyString())).thenReturn(Optional.of(material));
        when(this.materialRepository.save(any(Material.class))).thenReturn(material);
        var res = this.materialService.update("id", materialUpdate);
        var api = getResponse(res);
        var data = extract(material, api);

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNull(api.getErrors());
        assertNotNull(api.getData());
        assertInstanceOf(Material.class, api.getData());
        assertTrue(data.isPresent());
        assertEquals(material.getId(), data.get().getId());
        assertEquals(materialUpdate.getFile(), data.get().getFileUrl());
        assertEquals(materialUpdate.getVideo(), data.get().getVideoUrl());
        assertEquals(materialUpdate.getDeadline(), new SimpleDateFormat(FinalVariable.DATE_FORMAT).format(data.get().getDeadline()));
        assertNotNull(data.get().getCategory());
        assertEquals(materialUpdate.getCategory(), data.get().getCategory().getName());
        assertEquals(materialUpdate.getTitle(), data.get().getTitle());
        assertEquals(materialUpdate.getPoint(), data.get().getPoint());
        assertNull(data.get().getTopic());
        assertEquals(materialUpdate.getClassId(), data.get().getClassEntity().getId());
        reset(this.materialRepository, this.categoryRepository, this.classRepository);

//		data material not found
        when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
        when(this.categoryRepository.findByNameEqualsIgnoreCase(anyString())).thenReturn(Optional.of(category));
        when(this.materialRepository.findById(anyString())).thenReturn(Optional.empty());
        when(this.materialRepository.save(any(Material.class))).thenReturn(material);
        res = this.materialService.update("id", materialUpdate);
        api = getResponse(res);
        data = extract(material, api);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertNotNull(api.getErrors());
        assertNull(api.getData());
        assertTrue(data.isEmpty());
        reset(this.materialRepository, this.categoryRepository, this.classRepository);

//		any exception
        when(this.classRepository.findById(anyString())).thenReturn(Optional.of(ClassServiceTest.classEntity));
        when(this.categoryRepository.findByNameEqualsIgnoreCase(anyString())).thenReturn(Optional.of(category));
        when(this.materialRepository.findById(anyString())).thenThrow(AnyException.class);
        when(this.materialRepository.save(any(Material.class))).thenReturn(material);
        res = this.materialService.update("id", materialUpdate);
        api = getResponse(res);
        data = extract(material, api);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertNotNull(api.getErrors());
        assertNull(api.getData());
        assertTrue(data.isEmpty());
        reset(this.materialRepository, this.categoryRepository, this.classRepository);

    }

    @Test
    public void testDeleteById() {
//		success
        when(this.materialRepository.findById(anyString())).thenReturn(Optional.of(material));
        doNothing().when(this.materialRepository).deleteById(anyString());
        var res = this.materialService.deleteById("id");
        var api = getResponse(res);
        var data = extract(deleted(material), api);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNull(api.getErrors());
        assertTrue(api.isStatus());
        assertInstanceOf(ResponseDelete.class, api.getData());
        assertTrue(data.isPresent());
        reset(this.materialRepository);

//		material not found
        when(this.materialRepository.findById(anyString())).thenReturn(Optional.empty());
        doNothing().when(this.materialRepository).deleteById(anyString());
        res = this.materialService.deleteById("id");
        api = getResponse(res);
        data = extract(deleted(material), api);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertNotNull(api.getErrors());
        assertFalse(api.isStatus());
        assertTrue(data.isEmpty());
        reset(this.materialRepository);

//		any exception
        when(this.materialRepository.findById(anyString())).thenThrow(AnyException.class);
        doThrow(NullPointerException.class).when(this.materialRepository).deleteById(anyString());
        res = this.materialService.deleteById("id");
        api = getResponse(res);
        data = extract(deleted(material), api);
        assertNull(api.getData());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertNotNull(api.getErrors());
        assertFalse(api.isStatus());
        assertTrue(data.isEmpty());
        reset(this.materialRepository);
    }

    @Test
    @Disabled
    public void testFindAllByClassId() {
//		success
        when(this.materialRepository.findByClassEntityIdOrderByCreatedAtAsc(anyString())).thenReturn(Optional.of(new ArrayList<>(List.of(material))));
        var res = this.materialService.findAllByClassId("id");
        var api = getResponse(res);
        var materials = extract(new ArrayList<Material>(), api);

        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNull(api.getErrors());
        assertTrue(api.isStatus());
        assertNotNull(api.getData());
        assertInstanceOf(List.class, api.getData());
        assertTrue(materials.isPresent());
        assertEquals(1, materials.get().size());
        assertEquals(material, materials.get().get(0));
        reset(this.materialRepository, this.classRepository);

//		failed
        when(this.materialRepository.findByClassEntityIdOrderByCreatedAtAsc(anyString())).thenReturn(Optional.of(new ArrayList<>(List.of())));
        res = this.materialService.findAllByClassId("id");
        api = getResponse(res);
        materials = extract(new ArrayList<Material>(), api);

        assertNotNull(res);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertNull(api.getErrors());
        assertTrue(api.isStatus());
        assertNotNull(api.getData());
        assertInstanceOf(List.class, api.getData());
        assertTrue(materials.isPresent());
        reset(this.materialRepository, this.classRepository);

//		class not found
        when(this.materialRepository.findByClassEntityIdOrderByCreatedAtAsc(anyString())).thenReturn(Optional.empty());
        res = this.materialService.findAllByClassId("id");
        api = getResponse(res);
        materials = extract(new ArrayList<Material>(), api);

        assertNotNull(res);
        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertNotNull(api.getErrors());
        assertFalse(api.isStatus());
        assertNull(api.getData());
        assertTrue(materials.isEmpty());
        reset(this.materialRepository, this.classRepository);

//		internal server error
        when(this.materialRepository.findByClassEntityIdOrderByCreatedAtAsc(anyString())).thenThrow(AnyException.class);
        res = this.materialService.findAllByClassId("id");
        api = getResponse(res);
        materials = extract(new ArrayList<Material>(), api);

        assertNotNull(res);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertNotNull(api.getErrors());
        assertFalse(api.isStatus());
        assertNull(api.getData());
        assertTrue(materials.isEmpty());
        reset(this.materialRepository, this.classRepository);
    }

    @Test
    @Disabled
    public void testFindById() {
//		success
        when(this.materialRepository.findById(anyString())).thenReturn(Optional.of(material));
        var res = this.materialService.findById("id");
        var api = getResponse(res);
        var data = extract(material, api);

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertTrue(api.isStatus());
        assertNull(api.getErrors());
        assertNotNull(api.getData());
        assertInstanceOf(Material.class, api.getData());
        assertTrue(data.isPresent());
        assertEquals(material.getId(), data.get().getId());
        assertEquals(material.getTitle(), data.get().getTitle());
        assertEquals(material.getDeadline(), data.get().getDeadline());
        assertEquals(material.getContent(), data.get().getContent());
        assertEquals(material.getClassEntity(), data.get().getClassEntity());
        assertEquals(material.getCategory(), data.get().getCategory());
        assertEquals(material.getVideoUrl(), data.get().getVideoUrl());
        assertEquals(material.getFileUrl(), data.get().getFileUrl());
        assertEquals(material.getCreatedBy(), data.get().getCreatedBy());
        assertEquals(material.getCreatedAt(), data.get().getCreatedAt());
        assertEquals(material.getUpdatedBy(), data.get().getUpdatedBy());
        assertEquals(material.getUpdatedAt(), data.get().getUpdatedAt());
        assertEquals(material.getIsDeleted(), data.get().getIsDeleted());
        reset(this.materialRepository, this.classRepository);

//		data not found
        when(this.materialRepository.findById(anyString())).thenReturn(Optional.empty());
        res = this.materialService.findById("id");
        api = getResponse(res);
        data = extract(material, api);

        assertEquals(HttpStatus.BAD_REQUEST, res.getStatusCode());
        assertFalse(api.isStatus());
        assertNotNull(api.getErrors());
        assertNull(api.getData());
        assertTrue(data.isEmpty());
        assertInstanceOf(HashMap.class, api.getErrors());
        assertNotNull(((HashMap<String, Object>) api.getErrors()).get("message"));

//		test any exception
        when(this.materialRepository.findById(anyString())).thenThrow(AnyException.class);
        res = this.materialService.findById("id");
        api = getResponse(res);
        data = extract(material, api);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, res.getStatusCode());
        assertFalse(api.isStatus());
        assertNotNull(api.getErrors());
        assertNull(api.getData());
        assertTrue(data.isEmpty());
        assertInstanceOf(HashMap.class, api.getErrors());
        assertNotNull(((HashMap<String, Object>) api.getErrors()).get("message"));

    }
}
