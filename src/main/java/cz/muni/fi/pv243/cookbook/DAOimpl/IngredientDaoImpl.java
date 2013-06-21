package cz.muni.fi.pv243.cookbook.DAOimpl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.cookbook.DAO.IngredientDao;
import cz.muni.fi.pv243.cookbook.model.Ingredient;

@Stateless
public class IngredientDaoImpl implements IngredientDao {

	@PersistenceContext
	private EntityManager manager;

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

		return manager.find(Ingredient.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ingredient> retrieveAllIngredients() {
		Query query = manager.createQuery("SELECT ingr FROM Ingredient ingr");
		List<Ingredient> resultList = (List<Ingredient>) query.getResultList();
		return resultList;
	}

}
