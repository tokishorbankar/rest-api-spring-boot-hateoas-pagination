package com.example.web.assembers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.web.entities.RecipeEntity;
import com.example.web.model.RecipeModel;
import com.example.web.rest.RecipeRestController;

@Component
public class RecipeModelAssembler extends RepresentationModelAssemblerSupport<RecipeEntity, RecipeModel> {

	public RecipeModelAssembler() {
		super(RecipeRestController.class, RecipeModel.class);
	}

	@Override
	public CollectionModel<RecipeModel> toCollectionModel(Iterable<? extends RecipeEntity> entities) {
		CollectionModel<RecipeModel> models = super.toCollectionModel(entities);

		models.add(linkTo(methodOn(RecipeRestController.class).getRecipes()).withSelfRel());

		return models;
	}

	@Override
	public RecipeModel toModel(RecipeEntity entity) {
		RecipeModel model = instantiateModel(entity);

		model.add(linkTo(methodOn(RecipeRestController.class).getRecipeById(entity.getId())).withSelfRel());

		model.setId(entity.getId());
		model.setNumber(entity.getNumber());
		model.setName(entity.getName());

		return model;
	}

}
