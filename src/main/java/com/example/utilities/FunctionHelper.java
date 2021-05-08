package com.example.utilities;

import java.time.LocalDateTime;

import com.example.web.entities.RecipeEntity;
import com.example.web.model.RecipeModel;

public final class FunctionHelper {

	public static LocalDateTime getCurrentLocalDateTime() {
		return LocalDateTime.now();
	}

	public static RecipeEntity dtoToEntity(RecipeModel recipeModel) {
		return RecipeEntity.builder().number(recipeModel.getNumber()).name(recipeModel.getName()).build();

	}

	public static RecipeModel entityToDto(RecipeEntity recipeEntity) {
		return RecipeModel.builder().id(recipeEntity.getId()).number(recipeEntity.getNumber())
				.name(recipeEntity.getName()).build();

	}

	
}
