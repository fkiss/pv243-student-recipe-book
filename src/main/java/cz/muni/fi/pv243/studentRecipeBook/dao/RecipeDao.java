package cz.muni.fi.pv243.studentRecipeBook.dao;

import java.util.List;

import javax.ejb.Local;

import cz.muni.fi.pv243.studentRecipeBook.entities.Ingredient;
import cz.muni.fi.pv243.studentRecipeBook.entities.Recipe;

@Local
public interface RecipeDao {

	public void createRecipe(Recipe recipe);
	
	public void updateRecipe(Recipe recipe);
	
	public void deleteRecipe(Recipe recipe);

	public Recipe findRecipeByName(String name);
	
	public Recipe findRecipeById(Long id);
	
	public List<Recipe> retrieveAllRecipes();
	
}
