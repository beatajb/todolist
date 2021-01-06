package com.example.demo.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.CategoryNotFoundException;
import com.example.demo.model.CategoryModelAssembler;
import com.example.demo.model.ItemCategory;
import com.example.demo.repositories.CategoryRepository;

import lombok.NoArgsConstructor;

@RestController
@NoArgsConstructor
public class CategoriesController {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CategoryModelAssembler categoryAssembler;

	@GetMapping("/categories")
	public CollectionModel<EntityModel<ItemCategory>> all() {

		List<EntityModel<ItemCategory>> categories = categoryRepository.findAll().stream()
				.map(categoryAssembler::toModel).collect(Collectors.toList());

		return CollectionModel.of(categories, linkTo(methodOn(ItemsController.class).all()).withSelfRel());
	}

	@PostMapping("/categories")
	public ResponseEntity<?> newItem(@Valid @RequestBody ItemCategory newCategory) {
		EntityModel<ItemCategory> entityModel = categoryAssembler.toModel(categoryRepository.save(newCategory));

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@GetMapping("/categories/{categoryId}")
	public EntityModel<ItemCategory> one(@PathVariable Long categoryId) {
		ItemCategory category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new CategoryNotFoundException(categoryId));
		return categoryAssembler.toModel(category);
	}

	@PutMapping("/categories/{categoryId}")
	public ResponseEntity<EntityModel<ItemCategory>> replaceItem(@Valid @RequestBody ItemCategory newCategory,
			@PathVariable Long categoryId) {

		ItemCategory updatedCategory = categoryRepository.findById(categoryId).map(category -> {
			category.setCategoryName(newCategory.getCategoryName());
			return categoryRepository.save(category);
		}).orElseThrow(() -> new CategoryNotFoundException(categoryId));

		EntityModel<ItemCategory> entityModel = categoryAssembler.toModel(updatedCategory);

		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/categories/{categoryId}")
	public ResponseEntity<Object> deleteCategory(@PathVariable Long categoryId) {
		categoryRepository.deleteById(categoryId);
		return ResponseEntity.noContent().build();
	}
}
