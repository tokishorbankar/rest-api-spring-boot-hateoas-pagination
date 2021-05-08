package com.example.web.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "recipes", itemRelation = "recipe")
@JsonInclude(Include.NON_NULL)
public class RecipeModel extends RepresentationModel<RecipeModel> {

	private Long id;
	
	@NotEmpty(message = "Number must not empty")
	@NotNull(message = "Number must not null")
	private String number;
	
	private String name;
	
	

}
