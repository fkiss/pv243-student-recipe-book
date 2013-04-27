package cz.muni.fi.pv243.studentRecipeBook.dao;

import java.util.List;

import cz.muni.fi.pv243.studentRecipeBook.entities.Ingredient;
import cz.muni.fi.pv243.studentRecipeBook.entities.Recipe;

public interface RecipeDao {

	public void createRecipe(Recipe recipe);
	
	public void updateRecipe(Recipe recipe);
	
	public void deleteRecipe(Recipe recipe);
	
	public Recipe findRecipeByName(String name);
	
	public List<Recipe> findRecipesByIngredients(List<Ingredient> ingredients);
	
	public List<Recipe> retrieveAllRecipes();
	
}
