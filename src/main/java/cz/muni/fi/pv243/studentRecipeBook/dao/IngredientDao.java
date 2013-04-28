package cz.muni.fi.pv243.studentRecipeBook.dao;

import java.util.List;

import javax.ejb.Local;

import cz.muni.fi.pv243.studentRecipeBook.entities.Ingredient;

@Local
public interface IngredientDao {

	public void createIngredient(Ingredient ingredient);
	
	public void updateIngredient(Ingredient ingredient);
	
	public void deleteIngredient(Ingredient ingredient);
	
	public Ingredient retrieveIngredient(Long id);
	
	public List<Ingredient> retrieveAllIngredients();
}
