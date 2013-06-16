package cz.muni.fi.pv243.cookbook.finders;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pv243.cookbook.DAO.RecipeDao;
import cz.muni.fi.pv243.cookbook.model.Recipe;

@Named
@RequestScoped
public class RecipeFinder {

	@Inject
	private RecipeDao recipeDao;

	public RecipeFinder() {
	}

	public List<Recipe> findRecipes(String attributes) {
		
		List<Recipe> recipeList = new ArrayList<Recipe>();
		
		recipeList = findRecipeByName(attributes);
		
		//TODO : pridat aj s tymi atributmi
		// mozno pridat aj hladanie podla inych parametrov - nieco rychle, alebo tak...
		// "rozsirene volby hladania"
		
		// celkovo, hluposti to robi, treba dorobit...
		
		if(recipeList.isEmpty()) {
			
			throw new IllegalArgumentException("No recipes found!");
		}
		
		return recipeList;
	}

	private List<Recipe> findRecipeByName(String imputRecipeName) {

		List<Recipe> recipeList = new ArrayList<Recipe>();
		List<Recipe> allRecipes = recipeDao.retrieveAllRecipes();

		if (imputRecipeName == null || imputRecipeName.trim().equals("")) {

			throw new IllegalArgumentException("Argument is null");
		}

		for (Recipe recipe : allRecipes) {

			if (recipe.getName().contains(imputRecipeName)) {
				recipeList.add(recipe);
			}
		}

		return recipeList;
	}

	private List<Recipe> findRecipeByIngredients(String imputAttributes) {

		return null;
	}
//
//	public String getAttributes() {
//		return attributes;
//	}
//
//	public void setAttributes(String attributes) {
//		this.attributes = attributes;
//	}
//	
	//TODO : vyspickovat ten tokenizer, osetrit vs. mozne 
	private List<String> tokenizeInputString(String input) {
		
		List<String> tokenList = new ArrayList<String>();
		String[] tokens = input.split(",\\s");
		
	    for (String token : tokens) {

	    	tokenList.add(token);
	    }
		
		return tokenList;
	}
}
