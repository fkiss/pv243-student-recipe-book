package cz.muni.fi.pv243.cookbook.controller;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.cookbook.DAO.RecipeDao;
import cz.muni.fi.pv243.cookbook.login.Login;
import cz.muni.fi.pv243.cookbook.model.Recipe;
import cz.muni.fi.pv243.cookbook.model.User;

@Stateful
@SessionScoped
public class GetSpecificUserRecipes {

	@PersistenceContext
	private EntityManager manager;

	@Inject
	private Login login;

	@Produces
	@Model
	public List<Recipe> getAllSpecificUserRecipes() {

		List<Recipe> recipeList = null;
		
		if (login.isLoggedIn()) {
			User user = login.getCurrentUser();

			try {
				Query query = manager.createQuery(
						"select r from Recipe r where r.owner=:user",
						Recipe.class).setParameter("user", user);
				recipeList = query.getResultList();
			} catch (NoResultException e) {
				
			}
		}
		
		return recipeList;
	}
}
