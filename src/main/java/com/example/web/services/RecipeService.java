package com.example.web.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.exception.DataBaseTransactionException;
import com.example.web.entities.RecipeEntity;
import com.example.web.repositories.RecipeRepository;

@Service
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;

	public List<RecipeEntity> findAll() {
		return recipeRepository.findAll();
	}

	public Optional<RecipeEntity> findById(final Long id) {
		return recipeRepository.findById(id);
	}

	public Page<RecipeEntity> findAll(final Pageable pageable) {
		return recipeRepository.findAll(pageable);
	}

	public RecipeEntity saveOrUpdate(RecipeEntity recipeEntity) throws DataBaseTransactionException {
		try {
			return recipeRepository.save(recipeEntity);
		} catch (Exception e) {

			throw new DataBaseTransactionException();
		}
	}

	public void deleteById(Long id) throws DataBaseTransactionException {

		try {
			recipeRepository.deleteById(id);
		} catch (Exception e) {

			throw new DataBaseTransactionException(id);
		}
	}

}
