package cz.muni.fi.pv243.cookbook.DAOimpl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.cookbook.DAO.RecipeDao;
//import cz.muni.fi.pv243.cookbook.logging.RecipeLogger;
import cz.muni.fi.pv243.cookbook.model.Recipe;
//import javax.inject.Inject;
//import org.jboss.solder.logging.Logger;

@Stateless
public class RecipeDaoImpl implements RecipeDao {

	@PersistenceContext
	private EntityManager manager;
        
//                    @Inject
//	private Logger log;
                    
//                    @Inject
//	private RecipeLogger recipeLogger;

	@Override
	public void createRecipe(Recipe recipe) {

		validateRecipe(recipe);

		manager.persist(recipe);
//                                        recipeLogger.created(recipe.getName());
	}

	@Override
	public void removeRecipe(Recipe recipe) {

		validateRecipe(recipe);

		Recipe r = manager.find(Recipe.class, recipe.getId());
		manager.remove(r);
//                                        recipeLogger.deleted(recipe.getName());
	}

	@Override
	public void editRecipe(Recipe recipe) {

		validateRecipe(recipe);

		Recipe editedRecipe = manager.find(Recipe.class, recipe.getId());

		editedRecipe.setName(recipe.getName());
		editedRecipe.setOwner(recipe.getOwner());
		editedRecipe.setDescription(recipe.getDescription());
		editedRecipe.setFoodCategory(recipe.getFoodCategory());
		editedRecipe.setPortions(recipe.getPortions());
		editedRecipe.setTime(recipe.getTime());
		editedRecipe.setPrice(recipe.getPrice());
		editedRecipe.setStars(recipe.getStars());
		editedRecipe.setOneSentenceDescription(recipe.getOneSentenceDescription());
		editedRecipe.setIngredientList(recipe.getIngredientList());

		manager.merge(editedRecipe);
//                                        recipeLogger.edited(recipe.getName());

	}

	@Override
	public Recipe findRecipeByName(String name) {

		try {
			Query query = manager.createQuery(
					"select r from Recipe r where r.name=:name").setParameter(
					"name", name);
//                                                            recipeLogger.found(name);
                                                            
			return (Recipe) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Recipe findRecipeById(Long id) {
            
                                        Recipe recipe = manager.find(Recipe.class, id);
//                                        recipeLogger.found(recipe.getName());
                                        
		return recipe;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recipe> retrieveAllRecipes() {
            
//                                        log.info("Retrieving all recipes.");
                                        
		Query query = manager.createQuery("select r from Recipe r");
                
		return (List<Recipe>) query.getResultList();
	}

	private void validateRecipe(Recipe recipe) {
                                        
//                                        log.info("Validating all recipes.");
                                        
		if (recipe == null) {
			throw new IllegalArgumentException("recipe is null");
		}

		if (recipe.getOwner() == null) {
			throw new IllegalArgumentException("recipe author is null");
		}

		if (recipe.getIngredientList() == null) {
			throw new IllegalArgumentException("recipe has no ingredients");
		}
	}
}
