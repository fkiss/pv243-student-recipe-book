package cz.muni.fi.pv243.cookbook.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pv243.cookbook.DAO.RecipeDao;
import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.DAOimpl.RecipeDaoImpl;
import cz.muni.fi.pv243.cookbook.model.Recipe;
import cz.muni.fi.pv243.cookbook.model.User;

@RequestScoped
@Named
public class AdminOperationsController implements Serializable {

	private static final long serialVersionUID = 32423513432423425L;

	@Inject
	UserDao userDao;
	
	@Inject
	RecipeDao recipeDao;

	/**
	 * admin can't delete another admin, and even himself
	 * 
	 * @param id - id of the user to be deleted
	 * @return
	 */
	public String deleteUser(Long id) {
		
		List<Recipe> allRecipesList = recipeDao.retrieveAllRecipes();
		
		for(Recipe r: allRecipesList) {
			if(r.getOwner().getId() == id) {
				recipeDao.removeRecipe(r);
			}
		}
		
		User userToDelete = userDao.findUserByID(id);
		
		if(!userToDelete.isAdmin()) {
			
			userDao.removeUser(userToDelete);
		}

		return "admin/userList";
	}

	/**
	 * delete recipe
	 * 
	 * @param id - id of the recipe
	 * @return
	 */
	public String deleteRecipe(Long id) {
		
		recipeDao.removeRecipe(recipeDao.findRecipeById(id));
		
		return "admin/allRecipesList";
	}
	
    public List<User> getAllUsers () {
    	
		return userDao.retreiveAllUsers();    	
    }

    public List<Recipe> getAllRecipes () {
    	
    	return recipeDao.retrieveAllRecipes();
    }
}
