package cz.muni.fi.pv243.cookbook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.seam.security.Identity;

import cz.muni.fi.pv243.cookbook.DAO.RecipeDao;
import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.login.Login;
import cz.muni.fi.pv243.cookbook.model.Recipe;
import cz.muni.fi.pv243.cookbook.model.User;

@RequestScoped
public class GetSpecificUserRecipes {

	@PersistenceContext
	private EntityManager manager;

	@Inject
	private Identity identity;

	@Inject
	private UserDao userDao;
	
	@Inject
	private RecipeDao recipeDao;
	
	@Named
	@Produces
	public List<Recipe> getAllSpecificUserRecipes() {
		
		List<Recipe> resultList = new ArrayList<Recipe>();

		User user = userDao.findUserByID(Long.parseLong(identity.getUser()
				.getId()));

		if (user != null) {

			List<Recipe> recipeList = recipeDao.retrieveAllRecipes();

			for (Recipe r : recipeList) {

				if (r.getOwner().getId() == user.getId()) {

					resultList.add(r);
				}
			}
		}

		return resultList;
	}
}
