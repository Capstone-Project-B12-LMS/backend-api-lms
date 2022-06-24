package com.capstoneprojectb12.lms.backendapilms.services;

import com.capstoneprojectb12.lms.backendapilms.models.entities.Category;

public class CategoryServiceTest {
	public static final Category category = Category.builder()
			.id("id")
			.name("category name")
			.description("-")
			.build();
}
