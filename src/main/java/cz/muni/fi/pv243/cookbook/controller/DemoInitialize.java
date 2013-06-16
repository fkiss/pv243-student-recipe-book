package cz.muni.fi.pv243.cookbook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.cookbook.DAO.IngredientDao;
import cz.muni.fi.pv243.cookbook.DAO.RecipeDao;
import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.categories.FoodCategory;
import cz.muni.fi.pv243.cookbook.model.Ingredient;
import cz.muni.fi.pv243.cookbook.model.Recipe;
import cz.muni.fi.pv243.cookbook.model.User;

@Singleton
@Startup
public class DemoInitialize {

	@PersistenceContext
	private EntityManager manager;

	@Inject
	private UserDao userDao;

	@Inject
	private RecipeDao recipeDao;

	@Inject
	private IngredientDao ingredientDao;

	@PostConstruct
	public void initialize() {

		userDao.createUser(new User("filip", "kiss", "filip", "filip@gmial.com",
				"filip", false));
		userDao.createUser(new User("marek", "tuharsky", "marek", "tuharsky@gmail.com",
				"marek", false));
		userDao.createUser(new User("roman", "romanovic", "roman", "roman@gmail.com",
				"roman", false));
		userDao.createUser(new User("tomas", "plevko", "tom",
				"tomas@gmail.com", "tom", true));

		User tomas = userDao.findUserByNick("tom");
		User roman = userDao.findUserByNick("roman");

		recipeDao
				.createRecipe(new Recipe("halusky",
						"nastruhaj zemiaky atd... ", tomas, FoodCategory.OTHER,
						4, 0, 213));
		recipeDao.createRecipe(new Recipe("kebap",
				"zoberieme maso, hranolky a tak dalej... ", tomas,
				FoodCategory.FLESH, 3, 0, 121));
		recipeDao.createRecipe(new Recipe("polievka", "zmiesame vodu, a pridame dalsie veci...",
				roman, FoodCategory.SOUP, 4, 0, 12));
		recipeDao.createRecipe(new Recipe("kolac",
				"spravime cesto a dame piect do rury...", roman, FoodCategory.DESSERT, 4,
				0, 12));

		Recipe halusky = recipeDao.findRecipeByName("halusky");
		Recipe kebap = recipeDao.findRecipeByName("kebap");
		Recipe polievka = recipeDao.findRecipeByName("polievka");
		Recipe kolac = recipeDao.findRecipeByName("kolac");

		List<Ingredient> haluskyList = new ArrayList<Ingredient>();

		haluskyList.add(new Ingredient("zemiaky", "take gulate najlepsie", "4",
				true));
		haluskyList.add(new Ingredient("vajcia", "stredne velke", "3", true));
		haluskyList.add(new Ingredient("mlieko", "kravske", "2 dl", true));
		haluskyList.add(new Ingredient("slanina", "bravcova", "20 dg", false));

		halusky.setIngredientList(haluskyList);

		kebap.getIngredientList()
				.add(new Ingredient("voda", "", "20 dl", true));
		kebap.getIngredientList().add(
				new Ingredient("zemiaky", "take gulate najlepsie", "4", true));
		kebap.getIngredientList().add(
				new Ingredient("bryndza", "kvalitna", "40 dg", true));

		polievka.getIngredientList()
				.add(new Ingredient("pivo", "", "0.5 l", false));
		polievka.getIngredientList().add(
				new Ingredient("korenie", "", "20 dl", false));
		polievka.getIngredientList().add(
				new Ingredient("cukor", "", "20 dg", false));
		polievka.getIngredientList().add(new Ingredient("voda", "", "20 dl", true));

		kolac.getIngredientList().add(
				new Ingredient("sol", "najlepsie alpska", "20 dg", true));
		kolac.getIngredientList().add(
				new Ingredient("muka", "stredne velky", "1", true));
		kolac.getIngredientList().add(
				new Ingredient("cibula", "stredne velka", "5", false));

		for (Ingredient ing : halusky.getIngredientList()) {
			ingredientDao.createIngredient(ing);
		}

		for (Ingredient ing : kebap.getIngredientList()) {
			ingredientDao.createIngredient(ing);
		}

		for (Ingredient ing : kolac.getIngredientList()) {
			ingredientDao.createIngredient(ing);
		}

		for (Ingredient ing : polievka.getIngredientList()) {
			ingredientDao.createIngredient(ing);
		}
	}
}