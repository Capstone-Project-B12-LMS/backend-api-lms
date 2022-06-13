package com.capstoneprojectb12.lms.backendapilms.controllers.rest.material;

import com.capstoneprojectb12.lms.backendapilms.controllers.rest.utils.Constant;
import com.capstoneprojectb12.lms.backendapilms.controllers.rest.utils.JSON;
import com.capstoneprojectb12.lms.backendapilms.services.MaterialService;
import com.capstoneprojectb12.lms.backendapilms.services.MaterialServiceTest;
import java.util.HashMap;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag(value = "materialControllerTest")
@ExtendWith(value = {MockitoExtension.class})
public class MaterialControllerTest {
	private static final HashMap<String, Object> materialNew = new HashMap<>() {{
		put("classId", MaterialServiceTest.materialNew.getClassId());
		put("category", MaterialServiceTest.materialNew.getCategory());
		put("content", MaterialServiceTest.materialNew.getContent());
	}};
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MaterialService materialService;
	
	@Test
	public void testMockNotNull() {
		assertNotNull(this.mockMvc);
	}
	
	@Test
	public void testSave() throws Exception {
//		success
		var request = JSON.create(materialNew);
		this.mockMvc.perform(post(Constant.BASE_URL + "/class/material")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(request))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		;
	}
}
