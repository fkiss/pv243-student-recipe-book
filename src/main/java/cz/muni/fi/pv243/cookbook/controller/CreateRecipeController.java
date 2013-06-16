package cz.muni.fi.pv243.cookbook.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;


import cz.muni.fi.pv243.cookbook.DAO.IngredientDao;
import cz.muni.fi.pv243.cookbook.DAO.RecipeDao;
import cz.muni.fi.pv243.cookbook.categories.FoodCategory;
import cz.muni.fi.pv243.cookbook.login.Login;
import cz.muni.fi.pv243.cookbook.model.Ingredient;
import cz.muni.fi.pv243.cookbook.model.Recipe;

@Named
@RequestScoped
public class CreateRecipeController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private RecipeDao recipeDao;
	
	@Inject
	private IngredientDao ingredientDao;

	@Inject
	private Login login;
	
	@Inject
	private CreateIngredientsController createIngredientList;

	private Recipe newRecipe = new Recipe();
	
	@Named
	@Produces
	public Recipe getNewRecipe() {
		return newRecipe;
	}
	
	public CreateRecipeController() {
	}
	
	public FoodCategory[] getFoodCategory() {
		
        return FoodCategory.values();
    }

	public void create() {

		if (login.isLoggedIn()) {

			try {
				
				newRecipe.setOwner(login.getCurrentUser());

				newRecipe.setStars(0);
				
				newRecipe.setIngredientList(createIngredientList.getIngredientList());
				
				recipeDao.createRecipe(newRecipe);
				
				String message = "recipe successfully created";
				facesContext.addMessage(null, new FacesMessage(message));
				
			} catch (Exception e) {
				String errorMessage = getRootErrorMessage(e);
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						errorMessage, "Recipe registration unsuccessful");
				facesContext.addMessage(null, m);
			}

		} else {
			
			facesContext.addMessage(null, new FacesMessage("please log in to insert recipe"));
		}
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Recipe creation failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}

}
