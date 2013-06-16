package cz.muni.fi.pv243.cookbook.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pv243.cookbook.DAO.IngredientDao;
import cz.muni.fi.pv243.cookbook.model.Ingredient;

@ConversationScoped
@Named
public class CreateIngredientsController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Named
	@Produces
	private Ingredient inputIngredient = new Ingredient();

	
	// TOTO NEJAK PREROB... aby ti to pisalo ak sa tam vyskytne problem... mozno bude treba hned ukladat tie ingredienty
	private FacesContext facesContext;
	
	@Inject
	private Conversation conversation;
	
	@Inject
	private IngredientDao ingredientDao;
	
	private List<Ingredient> ingredientList = new ArrayList<Ingredient>();

	public CreateIngredientsController() {
	}

	public void begin() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	public void cancel() {
		conversation.end();
	}

	public void addIngredient() {


        try {
    		Ingredient newIngredient = new Ingredient();
    		
    		newIngredient.setName(inputIngredient.getName());
    		newIngredient.setDescription(inputIngredient.getDescription());
    		newIngredient.setObligatory(inputIngredient.getObligatory());
    		newIngredient.setQuantity(inputIngredient.getQuantity());

    		ingredientDao.createIngredient(newIngredient);
    		
    		ingredientList.add(newIngredient);
        } catch (Exception e) {
        	
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
			facesContext.addMessage(null, m);
        }
	}

	public void deleteIngredient(Ingredient ingredientToRemove) {

		ingredientDao.removeIngredient(ingredientToRemove);
		
		ingredientList.remove(ingredientToRemove);
	}

	public List<Ingredient> getIngredientList() {
		return ingredientList;
	}

    private String getRootErrorMessage(Exception e) {
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            return errorMessage;
        }
        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        return errorMessage;
    }

}
