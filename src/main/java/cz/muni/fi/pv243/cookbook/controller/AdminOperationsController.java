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
import cz.muni.fi.pv243.cookbook.model.Recipe;
import cz.muni.fi.pv243.cookbook.model.User;

@SessionScoped
@Named
public class AdminOperationsController implements Serializable {

	private static final long serialVersionUID = 32423513432423425L;

	@Inject
	UserDao userDao;
	
	@Inject
	RecipeDao recipeDao;

	public String deleteUser(Long id) {

		userDao.removeUser(userDao.findUserByID(id));
		return "admin/userList";
	}

	public String deleteRecipe(Recipe recipe) {
		
		System.out.println(recipe.getName());
		
		recipeDao.removeRecipe(recipe);
		
		return "admin/allRecipesList";
	}
	
    @Produces
    @Model
    public List<User> getAllUsers () {
    	
		return userDao.retreiveAllUsers();    	
    }

    @Produces
    @Model
    public List<Recipe> getAllRecipes () {
    	
    	return recipeDao.retrieveAllRecipes();
    }
}
