package cz.muni.fi.pv243.cookbook.DAOimpl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.cookbook.DAO.IngredientDao;
import cz.muni.fi.pv243.cookbook.model.Ingredient;
import cz.muni.fi.pv243.logging.IngredientLogger;
import javax.inject.Inject;
import org.jboss.solder.logging.Logger;

@Stateless
public class IngredientDaoImpl implements IngredientDao {

	@PersistenceContext
	private EntityManager manager;
        
                    @Inject
	private Logger log;
                    
                    @Inject
	private IngredientLogger ingredientLogger;

	@Override
	public void createIngredient(Ingredient ingredient) {

		if (ingredient == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		if (ingredient.getId() != null) {
			throw new IllegalArgumentException(
					"Id of the ingredient is already set");
		}

		manager.persist(ingredient);
                                        ingredientLogger.created(ingredient.getName());
	}

	@Override
	public void editIngredient(Ingredient ingredient) {

		if (ingredient == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		Ingredient editedIngredient = manager.find(Ingredient.class,
				ingredient.getId());

		editedIngredient.setName(ingredient.getName());
		editedIngredient.setQuantity(ingredient.getQuantity());
		editedIngredient.setDescription(ingredient.getDescription());
		editedIngredient.setObligatory(ingredient.getObligatory());
                
                                        ingredientLogger.edited(ingredient.getName());
	}

	@Override
	public void removeIngredient(Ingredient ingredient) {

		if (ingredient == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		if (ingredient.getId() == null) {
			throw new IllegalArgumentException(
					"Id cannot be null, ingredient is not in the DB");
		}

		Ingredient ingredientToRemove = manager.find(Ingredient.class,
				ingredient.getId());

		manager.remove(ingredientToRemove);
                                        ingredientLogger.deleted(ingredient.getName());
	}

	@Override
	public void removeIngredientById(Long id) {

		Ingredient ingredientToRemove = findIngredientById(id);

		manager.remove(ingredientToRemove);
	}

	@Override
	public Ingredient findIngredientById(Long id) {
		
		if (id == null) {
			throw new IllegalArgumentException("Argument id is null");
		}
                                        
                                        Ingredient ingredient = manager.find(Ingredient.class, id);
                                        ingredientLogger.found(ingredient.getName());
                                        
		return ingredient;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ingredient> retrieveAllIngredients() {
            
                                 	log.info("Retrieving all ingredients");
                                        
		Query query = manager.createQuery("SELECT ingr FROM Ingredient ingr");
		List<Ingredient> resultList = (List<Ingredient>) query.getResultList();
                
		return resultList;
	}

}
