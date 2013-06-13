package cz.muni.fi.pv243.studentRecipeBook.daoImpl;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.studentRecipeBook.dao.IngredientDao;
import cz.muni.fi.pv243.studentRecipeBook.entities.Ingredient;

@Stateless
@LocalBean
public class IngredientDaoImpl implements IngredientDao {

	@PersistenceContext
	private EntityManager em;
	
	public void createIngredient(Ingredient ingredient) {
		if (ingredient == null) {
            throw new IllegalArgumentException("Argument je null");
        }

        if (ingredient.getId() != null) {
            throw new IllegalArgumentException("Id prisady uz je nastavene");
        }

        em.persist(ingredient);		
	}

	public void updateIngredient(Ingredient ingredient) {
		if (ingredient == null) {
            throw new IllegalArgumentException("Argument je null");
        }
        Ingredient i = em.find(Ingredient.class, ingredient.getId());
        i.setName(ingredient.getName());
        i.setQuantity(ingredient.getQuantity());
        i.setDescription(ingredient.getDescription());
        	
	}

	public void deleteIngredient(Ingredient ingredient) {
		if (ingredient == null) {
            throw new IllegalArgumentException("Argument is null");
        }

        if (ingredient.getId() == null) {
            throw new IllegalArgumentException("Id nemoze byt null, prisada nieje v databazi");
        }
        Ingredient i = em.find(Ingredient.class, ingredient.getId());
        em.remove(i);		
	}

	public Ingredient retrieveIngredient(Long id) {
		if (id == null) {
            throw new IllegalArgumentException("Argument je null");
        }
        if (id.longValue() < 0) {
            throw new IllegalArgumentException("Id je zaporne");
        }
        return em.find(Ingredient.class, id);
        
	}

	@SuppressWarnings("unchecked")
	public List<Ingredient> retrieveAllIngredients() {
        Query query = em.createQuery("SELECT ingr FROM ingredient ingr");
        List<Ingredient> resultList = (List<Ingredient>) query.getResultList();
        return resultList;
	}

}
