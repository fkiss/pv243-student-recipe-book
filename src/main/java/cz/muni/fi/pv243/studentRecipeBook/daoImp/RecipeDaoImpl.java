package cz.muni.fi.pv243.studentRecipeBook.daoImp;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.studentRecipeBook.dao.RecipeDao;
import cz.muni.fi.pv243.studentRecipeBook.entities.Ingredient;
import cz.muni.fi.pv243.studentRecipeBook.entities.Recipe;

@Stateless
public class RecipeDaoImpl implements RecipeDao{

	@PersistenceContext
	private EntityManager em;

	public void createRecipe(Recipe recipe) {
		validateRecipe(recipe);
		try {
            em.persist(recipe);
        } finally {
            em.close();
        }
	}

	public void updateRecipe(Recipe recipe) {
		validateRecipe(recipe);
		try{
			Recipe r = em.find(Recipe.class, recipe.getId());
			r.setName(recipe.getName());
			r.setOwner(recipe.getOwner());
			r.setIngredients(recipe.getIngredients());
		}
		finally{
			em.close();
		}
		
	}

	public void deleteRecipe(Recipe recipe) {
		validateRecipe(recipe);
		try {
            Recipe r = em.find(Recipe.class, recipe.getId());
            em.remove(r);
        } finally {
            em.close();
        }
		
	}

	public Recipe findRecipeByName(String name) {
		
        try {
            Query queryFindAll = em.createQuery("SELECT r from "
                    + "cz.muni.fi.p243.studentRecipeBook.Recipe r where r.name = :name")
                    .setParameter("name", name);
            Recipe recipe = (Recipe) queryFindAll.getSingleResult();
            return recipe;
        } finally {
            em.close();
        }
	}

	public List<Recipe> findRecipesByIngredients(List<Ingredient> ingredients) {
		Query queryFindAll = em.createQuery("SELECT r from "
                + "cz.muni.fi.p243.studentRecipeBook.Recipe r WHERE r.ingredients = :ingredients ")
                .setParameter("ingredients", ingredients);
        List<Recipe> recipes = (List<Recipe>) queryFindAll.getResultList();
        return recipes;
	}

	public List<Recipe> retrieveAllRecipes() {
		Query queryFindAll = em.createQuery("SELECT r from "
                + "cz.muni.fi.p243.studentRecipeBook.Recipe r ");
        List<Recipe> recipes = (List<Recipe>) queryFindAll.getResultList();
        return recipes;
	}
	
	private void validateRecipe(Recipe recipe){
		if(recipe == null){
			throw new IllegalArgumentException("recept je null");
		}
		
		if(recipe.getOwner() == null){
			throw new IllegalArgumentException("autor receptu je null");
		}
		
		if(recipe.getOwner().getId() == null){
			throw new IllegalArgumentException("autor receptu nema id");
		}
		
		if(recipe.getIngredients() == null){
			throw new IllegalArgumentException("prisady receptu su null");
		}
	}
}
