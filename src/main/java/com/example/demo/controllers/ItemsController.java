package com.example.demo.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

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

import com.example.demo.exceptions.ItemNotFoundException;
import com.example.demo.model.ItemModelAssembler;
import com.example.demo.model.ListItem;
import com.example.demo.repositories.ListItemsRepository;

@RestController
public class ItemsController {
	private final ListItemsRepository repository;
	private final ItemModelAssembler assembler;

	ItemsController(ListItemsRepository repository, ItemModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	@GetMapping("/items")
	public CollectionModel<EntityModel<ListItem>> all() {

		List<EntityModel<ListItem>> items = repository.findAll().stream()
				.map(assembler::toModel) 
				.collect(Collectors.toList());

		return CollectionModel.of(items, linkTo(methodOn(ItemsController.class).all()).withSelfRel());
	}

	@PostMapping("/items")
	public ResponseEntity<?> newItem(@RequestBody ListItem newItem) {
		EntityModel<ListItem> entityModel = assembler.toModel(repository.save(newItem));

		  return ResponseEntity
		      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
		      .body(entityModel);
	}

	@GetMapping("/items/{id}")
	public EntityModel<ListItem> one(@PathVariable Long id) {
		ListItem item = repository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));
		return assembler.toModel(item);
	}

	@PutMapping("/items/{id}")
	public ResponseEntity<EntityModel<ListItem>> replaceItem(@RequestBody ListItem newItem, @PathVariable Long id) {

		  ListItem updatedItem = repository.findById(id) 
			      .map(item -> {
			        item.setTaskName(newItem.getTaskName());
			        return repository.save(item);
			      }) //
			      .orElseGet(() -> {
			        newItem.setItemId(id);
			        return repository.save(newItem);
			      });

			  EntityModel<ListItem> entityModel = assembler.toModel(updatedItem);

			  return ResponseEntity
			      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
			      .body(entityModel);
	}

	@DeleteMapping("/items/{id}")
	public ResponseEntity<Object> deleteItem(@PathVariable Long id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
