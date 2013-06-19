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

import cz.muni.fi.pv243.cookbook.DAO.IngredientDao;
import cz.muni.fi.pv243.cookbook.DAO.RecipeDao;
import cz.muni.fi.pv243.cookbook.categories.FoodCategory;
import cz.muni.fi.pv243.cookbook.login.Login;
import cz.muni.fi.pv243.cookbook.model.Ingredient;
import cz.muni.fi.pv243.cookbook.model.Recipe;

@Named
@ConversationScoped
public class UpdateRecipeController implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	Conversation conversation;

	@Inject
	private RecipeDao recipeDao;

	@Inject
	private IngredientDao ingredientDao;
	
	private Recipe recipe;

	@Named
	@Produces
	private Ingredient editIngredient = new Ingredient();

	private Long id;

	@Inject
	private Login login;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FoodCategory[] getFoodCategory() {
		
        return FoodCategory.values();
    } 

	//TODO : prerobiť, nefunguje ako by malo...
	
	@Named
	@Produces
	private List<Ingredient> ingredientList = new ArrayList<Ingredient>();

	public void retrieve() {

		if (conversation.isTransient()) {
			
			conversation.begin();
			
			recipe = recipeDao.findRecipeById(id);
			ingredientList.addAll(recipe.getIngredientList());
			
			recipe.getIngredientList().clear();
			recipeDao.editRecipe(recipe);
		}
	}

	public Recipe getRecipe() {
		
		return recipe;
	}

	public List<Ingredient> getIngredientList() {
		return ingredientList;
	}
	
	public void addIngredient() {

        try {
    		Ingredient newIngredient = new Ingredient();
    		
    		newIngredient.setName(editIngredient.getName());
    		newIngredient.setDescription(editIngredient.getDescription());
    		newIngredient.setObligatory(editIngredient.getObligatory());
    		newIngredient.setQuantity(editIngredient.getQuantity());

    		ingredientList.add(newIngredient);
    		
    		ingredientDao.createIngredient(newIngredient);
    		
        } catch (Exception e) {
        	System.out.println(e);
        	//TODO : pridat do logu...
        }
	}

	public void deleteIngredient(Ingredient ingredientToRemove) {

		ingredientDao.removeIngredient(ingredientToRemove);
		
		ingredientList.remove(ingredientToRemove);		
	}
	

	public void update() {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {

			if (login.isLoggedIn()) {
				recipe.setIngredientList(getIngredientList());
				recipeDao.editRecipe(recipe);
				String message = "recipe successfully updated";
				facesContext.addMessage(null, new FacesMessage(message));

				conversation.end();

			} else {

				facesContext
						.addMessage(null, new FacesMessage("please log in"));

			}

		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Update unsuccessful");
			facesContext.addMessage(null, m);
			conversation.end();
		}
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Registration failed. See server log for more information";
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
