package cz.muni.fi.pv243.cookbook.finders;

import cz.muni.fi.pv243.cookbook.DAO.IngredientDao;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pv243.cookbook.DAO.RecipeDao;
import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.controller.GetAllUsersController;
import cz.muni.fi.pv243.cookbook.model.Ingredient;
import cz.muni.fi.pv243.cookbook.model.Recipe;
import cz.muni.fi.pv243.cookbook.model.User;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@Named
@RequestScoped
public class RecipeFinder {

	@Inject
	private RecipeDao recipeDao;
        
                    @Inject
                    private IngredientDao ingredientDao;
                    
                    @Inject UserDao userDao;
        
                    private GetAllUsersController getAllUsersController;
                            
	public RecipeFinder() {
	}

	public List<Recipe> searchEverywhere(String attributes) {
		
		List<Recipe> recipeList = new ArrayList<Recipe>();
                                        List<User> userList = new ArrayList<User>();
                                        
		recipeList.addAll(findRecipeByName(attributes));
                                        recipeList.addAll(findRecipeByIngredients(attributes));
                                        //userList = getAllUsersController.getAllUsers(); 
                                        //vyriesit user recognization vJSF

		if(recipeList.isEmpty()) {
			
			//throw new IllegalArgumentException("No recipes found!");
		}
		
		return recipeList;
	}

	private List<Recipe> findRecipeByName(String inputRecipeName) {

		List<Recipe> recipeList = new ArrayList<Recipe>();
		List<Recipe> allRecipes = recipeDao.retrieveAllRecipes();

		if (inputRecipeName == null || inputRecipeName.trim().equals("")) {

			throw new IllegalArgumentException("Argument is null");
		}

		for (Recipe recipe : allRecipes) {  

			if (recipe.getName().contains(inputRecipeName)) {
				recipeList.add(recipe);
			}
		}

		return recipeList;
	}
        
//                    private List<User> findUserByNameOrNick(String inputUserName) {
//
//		List<User> userList = new ArrayList<User>();
//                                        List<User> allUsers = new ArrayList<User>(); //TODO: getAllUsers 
//		
//                                        if (inputUserName == null || inputUserName.trim().equals("")) {
//
//			throw new IllegalArgumentException("Argument is null");
//		}
//                                        userList.add(userDao.findUserByNick(inputUserName));
//
//		for (User user : allUsers) {  
//
//                                            if (user.getFirstName().contains(inputUserName) || user.getSurname().contains(inputUserName) || (user.getFirstName() + user.getSurname()).contains(inputUserName) || (user.getSurname()+ user.getFirstName()).contains(inputUserName)) {
//                                                userList.add(user);
//                                            }
//		}
//
//		return userList;
//	}
        
                    private Ingredient findIngredientByName(String inputIngredientName) {

		Ingredient wantedIngredient = new Ingredient();
		List<Ingredient> allIngredients = ingredientDao.retrieveAllIngredients();

		if (inputIngredientName == null || inputIngredientName.trim().equals("")) {

			throw new IllegalArgumentException("Argument is null");
		}

		for (Ingredient ingredient : allIngredients) {  

			if (ingredient.getName().contains(inputIngredientName)) {
				wantedIngredient = ingredient;
			}
		}

		return wantedIngredient;
	}

	private List<Recipe> findRecipeByIngredients(String attributes) {
                                        List<Recipe> recipeByIngredientList =  new ArrayList<Recipe>();
                                        List<Recipe> allRecipes = recipeDao.retrieveAllRecipes();

                                        List<String> tokenizedIngredients = tokenizeInputString(attributes);
                                        List<String> parsedIngredientsToString = new ArrayList<String>();
                                        
                                        List<Ingredient> actualIngredientsOfRecipe;
//                                        List<Ingredient> actualObligatoryIngredientsOfRecipe = new ArrayList<Ingredient>();;
                                        
                                        Map<Recipe, Integer> recipesWithCountedMatchings = new HashMap<Recipe, Integer>();                                        
                                        //counting matches of input Ingredients with each recipe
                                        //used Iterator instead of forEach because of removing recipe during cycle
                                        for (Iterator<Recipe> it = allRecipes.iterator(); it.hasNext();) {
                                            Recipe recipe = it.next();
                                            
                                            actualIngredientsOfRecipe = recipe.getIngredientList();
                                            Integer count = new Integer(0);
                                            
                                            //selecting only obligatory ingredients from current recipe
//                                            for(Ingredient ingredient : actualIngredientsOfRecipe){
//                                                if (ingredient.isObligatory()) {
//                                                        actualObligatoryIngredientsOfRecipe.add(ingredient);
//                                                }
//                                            }
                                            
                                            //converting Ingredient ingredients to String ingredients
                                            for(Ingredient ingredient : actualIngredientsOfRecipe){ //actualObligatoryIngredientsOfRecipe
                                                parsedIngredientsToString.add(ingredient.getName());
                                            }

                                            for (String ingredient: tokenizedIngredients) {

                                                if (parsedIngredientsToString.contains(ingredient)) {
                                                    count = new Integer(count.intValue()+1);
                                                    
                                                }  
                                            }

                                            if (count.equals(Integer.valueOf(0))) {
                                                it.remove();
                                            }
                                            
                                            parsedIngredientsToString.clear();
                                            
                                            //putting obtained data together
                                            if(count.intValue()>0){
                                                recipesWithCountedMatchings.put(recipe, count);
                                            }
                                                 
                                        }
                                        
                                        //sorting allRecipes with found matchings according to count of matching ingredients  
                                        List<Entry<Recipe,Integer>> entries = new ArrayList<Entry<Recipe,Integer>>(recipesWithCountedMatchings.entrySet());
                                        Collections.sort(entries, new Comparator<Entry<Recipe,Integer>>() {
                                                    @Override
                                                    public int compare(Entry<Recipe,Integer> e1, Entry<Recipe,Integer> e2) {
                                                            return e2.getValue().compareTo(e1.getValue()); // Sorts descending.
                                                    }
                                            });
                                        //inserting found recipes into list 
                                        for (Entry<Recipe,Integer> entry : entries) {
                                                    recipeByIngredientList.add(entry.getKey());
                                        }  
		return recipeByIngredientList;
	}
//
//	public String getAttributes() {
//		return attributes;
//	}
//
//	public void setAttributes(String attributes) {
//		this.attributes = attributes;
//	}
//	
	//TODO : vyspickovat ten tokenizer, osetrit vs. mozne 
	private List<String> tokenizeInputString(String input) {
		
		List<String> tokenList = new ArrayList<String>();
		String[] tokens = input.split(",\\s+");
		
	    for (String token : tokens) {

	    	tokenList.add(token);
	    }
		
		return tokenList;
	}
}
