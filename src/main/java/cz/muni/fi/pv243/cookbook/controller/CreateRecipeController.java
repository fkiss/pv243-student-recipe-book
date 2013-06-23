package cz.muni.fi.pv243.cookbook.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.security.Identity;

import cz.muni.fi.pv243.cookbook.DAO.IngredientDao;
import cz.muni.fi.pv243.cookbook.DAO.RecipeDao;
import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.login.Login;
import cz.muni.fi.pv243.cookbook.model.Ingredient;
import cz.muni.fi.pv243.cookbook.model.Recipe;
import cz.muni.fi.pv243.cookbook.model.User;

/**
 * class used for updating the information about the recipes of particular
 * author
 * 
 * @author tomas plevko <xplevko@mail.muni.cz>
 * 
 */
@Named
@ConversationScoped
public class CreateRecipeController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private RecipeDao recipeDao;

	@Inject
	private IngredientDao ingredientDao;
	
	@Inject
	private UserDao userDao;

	@Named
	@Produces
	private Ingredient inputIngredient = new Ingredient();

	@Inject
	private Conversation conversation;

	@Inject
	private Identity identity;

	private Recipe recipe = new Recipe();

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * method for initialisation of the update process of the ingredient
	 */
	public void retrieve() {

		if (conversation.isTransient()) {

			conversation.begin();

			recipe = recipeDao.findRecipeById(id);
		}
	}

	@Named
	@Produces
	public Recipe getRecipe() {
		return recipe;
	}

	public void begin() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	// TODO : add cancel button into the page
	public void cancel() {

		conversation.end();
	}

	public List<Ingredient> getIngredientList() {
		return recipe.getIngredientList();
	}

	/**
	 * Method for adding the ingredients into the recipe
	 */
	public void addIngredient() {

		try {
			Ingredient newIngredient = new Ingredient();

			newIngredient.setName(inputIngredient.getName());
			newIngredient.setDescription(inputIngredient.getDescription());
			newIngredient.setObligatory(inputIngredient.getObligatory());
			newIngredient.setQuantity(inputIngredient.getQuantity());

			recipe.getIngredientList().add(newIngredient);
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Method used for the removing of ingredients from the ingredient list of
	 * the recipe
	 * 
	 * @param ingredientToRemove
	 */
	public void deleteIngredient(Ingredient ingredientToRemove) {

		recipe.getIngredientList().remove(ingredientToRemove);
	}
	
	/**
	 * Method for creating new recipe
	 */

	public void createRecipe() {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {

			recipe.setOwner(userDao.findUserByID(Long.parseLong(identity.getUser().getId())));

			recipe.setStars(0);

			recipeDao.createRecipe(recipe);

			String message = "recipe successfully created";
			facesContext.addMessage(null, new FacesMessage(message));

			conversation.end();

		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Recipe registration unsuccessful");
			facesContext.addMessage(null, m);
		}
	}
	

	/**
	 * Method used for updating of the recipe, the user updates in the jsf page
	 */
	public void update() {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {

			recipeDao.editRecipe(recipe);
			String message = "recipe successfully updated";
			facesContext.addMessage(null, new FacesMessage(message));

			conversation.end();

		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Update unsuccessful");
			facesContext.addMessage(null, m);
			conversation.end();
		}
	}

	/**
	 * Method for deleting the recipe
	 * 
	 * @return userRecipes - the jsf page, containing the remaining recipes of
	 *         the user
	 */
	public String delete() {

		recipeDao.removeRecipe(recipe);
		return "userRecipes";
	}

	private String getRootErrorMessage(Exception e) {
		String errorMessage = "Recipe creation or update failed. See server log for more information";
		if (e == null) {
			return errorMessage;
		}
		Throwable t = e;
		while (t != null) {
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}

}
