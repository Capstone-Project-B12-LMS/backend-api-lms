package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Material;
import com.capstoneprojectb12.lms.backendapilms.models.repositories.MaterialRepository;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Tag(value = "materialServiceTest")
public class MaterialServiceTest {
	
	private final LocalDateTime deadline = LocalDateTime.now();
	private final Material material = Material.builder().id("id").classes(ClassServiceTest.classEntity).title("material title").content("material content").topic(null).videoUri("url").fileUrl("url").deadline(deadline).point(100).category(null).build();
	@MockBean
	private MaterialRepository materialRepository;
	@Autowired
	private MaterialService materialService;
	
	@Test
	public void testNotNull() {
		assertNotNull(materialRepository);
	}
	
	@Test
	public void testSave() {
//        success
		when(this.materialRepository.save(any(Material.class))).thenReturn(material);
		var result = this.materialService.save(material);
		assertTrue(result.isPresent());
		assertEquals(material.getContent(), result.get().getContent());
		assertEquals(deadline, result.get().getDeadline());

//        failed
		when(this.materialRepository.save(any(Material.class))).thenReturn(null);
		result = this.materialService.save(material);
		assertFalse(result.isPresent());
		assertThrows(NoSuchElementException.class, () -> this.materialService.save(material).get());
	}
}
