package cz.muni.fi.pv243.cookbook.controller;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pv243.cookbook.DAO.RecipeDao;
import cz.muni.fi.pv243.cookbook.model.Recipe;

@Named
@RequestScoped
public class ViewRecipeController {

	@Inject
	private RecipeDao recipeDao;
	
	@Produces
	private Recipe recipe;
	
	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void retrieve() {

			recipe = recipeDao.findRecipeById(id);
	}
	
	public Recipe getRecipe() {
		return recipe;
	}

}
