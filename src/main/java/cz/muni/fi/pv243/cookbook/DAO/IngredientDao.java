package cz.muni.fi.pv243.cookbook.DAO;

import java.util.List;

import cz.muni.fi.pv243.cookbook.model.Ingredient;

public interface IngredientDao {
	
	public void createIngredient(Ingredient ingredient);
	
	public void editIngredient(Ingredient ingredient);
	
	public void removeIngredient(Ingredient ingredient);
	
	public Ingredient findIngredientById(Long id);

	public void removeIngredientById(Long id);
	
	public List<Ingredient> retrieveAllIngredients();
	
}
