package com.example.demo.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.demo.controllers.ItemsController;

@Component
public class ItemModelAssembler implements RepresentationModelAssembler<ListItem, EntityModel<ListItem>> {

  @Override
  public EntityModel<ListItem> toModel(ListItem item) {

    return EntityModel.of(item,
        linkTo(methodOn(ItemsController.class).one(item.getItemId())).withSelfRel(),
        linkTo(methodOn(ItemsController.class).all()).withRel("items"));
  }
}
