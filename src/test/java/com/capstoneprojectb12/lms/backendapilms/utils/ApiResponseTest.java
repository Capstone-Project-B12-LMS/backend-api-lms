package com.capstoneprojectb12.lms.backendapilms.utils;

import com.capstoneprojectb12.lms.backendapilms.services.ClassServiceTest;
import com.capstoneprojectb12.lms.backendapilms.services.MaterialServiceTest;
import com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.capstoneprojectb12.lms.backendapilms.utilities.ApiResponse.extract;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag(value = "apiResponseTest")
public class ApiResponseTest {
	@Test
	public void testExtract() throws Exception {
		{
//		success
			var api = ApiResponse.success(MaterialServiceTest.material);
			var data = extract(MaterialServiceTest.material, api);
			assertTrue(data.isPresent());
			assertEquals(MaterialServiceTest.material.getId(), data.get().getId());
			assertEquals(MaterialServiceTest.material.getTitle(), data.get().getTitle());
			assertEquals(MaterialServiceTest.material.getVideoUrl(), data.get().getVideoUrl());
			assertEquals(MaterialServiceTest.material.getFileUrl(), data.get().getFileUrl());
			assertEquals(MaterialServiceTest.material.getPoint(), data.get().getPoint());
			assertEquals(MaterialServiceTest.material.getDeadline(), data.get().getDeadline());
			assertEquals(MaterialServiceTest.material.getContent(), data.get().getContent());
			assertEquals(MaterialServiceTest.material.getCategory(), data.get().getCategory());
			assertEquals(MaterialServiceTest.material.getClasses(), data.get().getClasses());
			assertEquals(MaterialServiceTest.material.getIsDeleted(), data.get().getIsDeleted());
		}
		
		{
//		failed
			var api = ApiResponse.success(ClassServiceTest.classEntity);
			var data = extract(MaterialServiceTest.material, api);
			assertTrue(data.isPresent());
			assertThrows(ClassCastException.class, () -> assertEquals(MaterialServiceTest.material.getCreatedBy(), data.get().getCreatedBy()));
		}
	}
}
