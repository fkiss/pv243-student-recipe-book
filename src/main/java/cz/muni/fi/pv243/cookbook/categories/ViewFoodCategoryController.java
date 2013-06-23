package cz.muni.fi.pv243.cookbook.categories;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import cz.muni.fi.pv243.cookbook.model.Recipe;

@RequestScoped
@Named
public class ViewFoodCategoryController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private EntityManager manager;

	@Produces
	private List<Recipe> recipeList = new ArrayList<Recipe>();

	private String foodCategory;

	public String getFoodCategory() {
		
		return foodCategory;
	}

	public void setFoodCategory(String foodCategory) {
		
		this.foodCategory = foodCategory;
	}

	// supress warning recipeList = (List<Recipe>) query.getResultList(), query creates warnings
	@SuppressWarnings("unchecked")
	public void retrieveFoodCategories() {
		
		foodCategory = foodCategory.toUpperCase();
		
		FoodCategory category = FoodCategory.valueOf(foodCategory);
		
		Query query = manager.createQuery(
				"select r from Recipe r where r.foodCategory=:category")
				.setParameter("category", category);
		
		recipeList = (List<Recipe>) query.getResultList();
	}

	public List<Recipe> getRecipeList() {

		return recipeList;
	}
}