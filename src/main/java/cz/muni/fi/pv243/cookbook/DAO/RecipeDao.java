package cz.muni.fi.pv243.cookbook.DAO;

import java.util.List;

import cz.muni.fi.pv243.cookbook.model.Recipe;

public interface RecipeDao {

	public void createRecipe(Recipe recipe);
	
	public void editRecipe(Recipe recipe);
	
	public void removeRecipe(Recipe recipe);
	
	public Recipe findRecipeByName(String name);
	
	public Recipe findRecipeById(Long id);
	
	public List<Recipe> retrieveAllRecipes();	
}
