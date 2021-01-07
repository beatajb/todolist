package com.example.demo.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.demo.controllers.CategoriesController;

@Component
public class CategoryModelAssembler implements RepresentationModelAssembler<ItemCategory, EntityModel<ItemCategory>> {

  @Override
  public EntityModel<ItemCategory> toModel(ItemCategory category) {

    return EntityModel.of(category,
        linkTo(methodOn(CategoriesController.class).one(category.getCategoryId())).withSelfRel(),
        linkTo(methodOn(CategoriesController.class).all()).withRel("categories"));
  }
}
