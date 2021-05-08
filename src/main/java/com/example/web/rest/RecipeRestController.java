package com.example.web.rest;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.utilities.FunctionHelper;
import com.example.web.assembers.RecipeModelAssembler;
import com.example.web.entities.RecipeEntity;
import com.example.web.model.RecipeModel;
import com.example.web.services.RecipeService;

@RestController
@RequestMapping("/api/services/recipe")
public class RecipeRestController {

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private RecipeModelAssembler recipeModelAssembler;

	@Autowired
	private PagedResourcesAssembler<RecipeEntity> pagedResourcesAssembler;

	private static final int DEFAULT_PAGE_NUMBER = 0;
	private static final int DEFAULT_PAGE_SIZE = 10;

	@GetMapping
	public ResponseEntity<CollectionModel<RecipeModel>> getRecipes() throws BadRequestException {

		final List<RecipeEntity> actorEntities = recipeService.findAll();

		return new ResponseEntity<>(recipeModelAssembler.toCollectionModel(actorEntities), HttpStatus.OK);
	}

	@GetMapping("/all-list")
	public ResponseEntity<PagedModel<RecipeModel>> getRecipesByPageable(
			@PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE) @SortDefault.SortDefaults({
					@SortDefault(sort = "id", direction = Sort.Direction.DESC) }) final Pageable pageable)
			throws BadRequestException {

		final Page<RecipeEntity> entities = recipeService.findAll(pageable);

		final PagedModel<RecipeModel> collModel = pagedResourcesAssembler.toModel(entities, recipeModelAssembler);

		return new ResponseEntity<>(collModel, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RecipeModel> getRecipeById(@PathVariable("id") final Long id)
			throws BadRequestException, ResourceNotFoundException {
		return recipeService.findById(id).map(recipeModelAssembler::toModel).map(ResponseEntity::ok)
				.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@PostMapping
	public ResponseEntity<RecipeModel> createRecipe(final @Valid @RequestBody RecipeModel recipeModel)
			throws BadRequestException {

		final RecipeModel results = FunctionHelper
				.entityToDto(recipeService.saveOrUpdate(FunctionHelper.dtoToEntity(recipeModel)));

		final URI url = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(results.getId())
				.toUri();

		return ResponseEntity.created(url).eTag(url.getPath()).body(results);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RecipeModel> updateRecipe(@PathVariable(required = true, value = "id") final Long id,
			final @Valid @RequestBody RecipeModel recipeModel) throws BadRequestException, ResourceNotFoundException {

		final RecipeEntity entity = recipeService.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

		final RecipeModel results = FunctionHelper.entityToDto(recipeService.saveOrUpdate(RecipeEntity.builder()
				.id(entity.getId()).name(recipeModel.getName()).number(recipeModel.getNumber()).build()));

		final URI url = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(results.getId())
				.toUri();

		return ResponseEntity.ok().location(url).eTag(url.getPath()).body(results);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRecipeById(@PathVariable("id") final Long id)
			throws BadRequestException, ResourceNotFoundException {

		final RecipeEntity results = recipeService.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

		recipeService.deleteById(results.getId());

		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

}
