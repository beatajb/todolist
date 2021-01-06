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
import com.example.demo.exceptions.DoubleItemException;
import com.example.demo.exceptions.ItemNotFoundException;
import com.example.demo.model.ItemCategory;
import com.example.demo.model.ItemModelAssembler;
import com.example.demo.model.ListItem;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ListItemsRepository;

import lombok.NoArgsConstructor;

@RestController
@NoArgsConstructor
public class ItemsController {
	
	@Autowired
	private ListItemsRepository itemRepository;
	@Autowired
	private ItemModelAssembler itemAssembler;
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/items")
	public CollectionModel<EntityModel<ListItem>> all() {

		List<EntityModel<ListItem>> items = itemRepository.findAll().stream()
				.map(itemAssembler::toModel) 
				.collect(Collectors.toList());

		return CollectionModel.of(items, linkTo(methodOn(ItemsController.class).all()).withSelfRel());
	}

	@PostMapping("/items")
	public ResponseEntity<?> newItem(@Valid @RequestBody ListItem newItem) {
		List<ListItem> doubleItem = itemRepository.findAll().stream()
				.filter(item->item.getTaskName().equals(newItem.getTaskName()))
				.collect(Collectors.toList());
		if(isDoubleItem(newItem)) {
			throw new DoubleItemException(doubleItem.get(0).getItemId(), doubleItem.get(0).getTaskName());
		}
		
		EntityModel<ListItem> entityModel = itemAssembler.toModel(itemRepository.save(newItem));

		  return ResponseEntity
		      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
		      .body(entityModel);
	}

	private boolean isDoubleItem(@Valid ListItem newItem) {
		List<ListItem> doubleItem = itemRepository.findAll().stream()
				.filter(item->item.getTaskName().equals(newItem.getTaskName()))
				.collect(Collectors.toList());
		return !doubleItem.isEmpty();
	}

	@GetMapping("/items/{itemId}")
	public EntityModel<ListItem> one(@PathVariable Long itemId) {
		ListItem item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
		return itemAssembler.toModel(item);
	}

	@PutMapping("/items/{itemId}")
	public ResponseEntity<EntityModel<ListItem>> replaceItem(@Valid @RequestBody ListItem newItem, @PathVariable Long itemId) {

		  ListItem updatedItem = itemRepository.findById(itemId) 
			      .map(item -> {
			        item.setTaskName(newItem.getTaskName());
			        return itemRepository.save(item);
			      }) //
			      .orElseThrow(() -> new ItemNotFoundException(itemId));

			  EntityModel<ListItem> entityModel = itemAssembler.toModel(updatedItem);

			  return ResponseEntity
			      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
			      .body(entityModel);
	}

	@DeleteMapping("/items/{itemId}")
	public ResponseEntity<Object> deleteItem(@PathVariable Long itemId) {
		itemRepository.deleteById(itemId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/items/{itemId}/addcategory/{categoryId}")
	public ResponseEntity<EntityModel<ListItem>> attachCategory(@PathVariable Long itemId, @PathVariable Long categoryId){
		
		ItemCategory category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
		
		ListItem updatedItem = itemRepository.findById(itemId) 
			      .map(item -> {
			        item.getCategories().add(category);
			        return itemRepository.save(item);
			      }) 
			      .orElseThrow(() -> new ItemNotFoundException(itemId));

			  EntityModel<ListItem> entityModel = itemAssembler.toModel(updatedItem);

			  return ResponseEntity
			      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
			      .body(entityModel);
	} 
	
	@PutMapping("/items/{itemId}/removecategory/{categoryId}")
	public ResponseEntity<EntityModel<ListItem>> removeCategory(@PathVariable Long itemId, @PathVariable Long categoryId){
		
		ItemCategory category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(categoryId));
		
		ListItem updatedItem = itemRepository.findById(itemId) 
			      .map(item -> {
			        item.getCategories().remove(category);
			        return itemRepository.save(item);
			      }) 
			      .orElseThrow(() -> new ItemNotFoundException(itemId));

			  EntityModel<ListItem> entityModel = itemAssembler.toModel(updatedItem);

			  return ResponseEntity
			      .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
			      .body(entityModel);
	} 
}
