package cz.muni.fi.pv243.cookbook.DAOimpl;

import cz.muni.fi.pv243.cookbook.DAO.RecipeDao;
import cz.muni.fi.pv243.cookbook.categories.FoodCategory;
import cz.muni.fi.pv243.cookbook.model.Recipe;
import cz.muni.fi.pv243.cookbook.model.User;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class RecipeDaoImplTest
{
   @Inject
   private RecipeDao recipeDao;
   
   
   @Deployment
   public static Archive<?> createTestArchive()
   {
      return ShrinkWrap.create(WebArchive.class, "test.war")
            .addClass(FoodCategory.class)
            .addPackages(true, RecipeDaoImpl.class.getPackage(), Recipe.class.getPackage(), RecipeDao.class.getPackage())
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsWebInfResource("test-ds.xml");
   }

   @Test
   public void testIsDeployed()
   {
      Assert.assertNotNull(recipeDao);
   }
   
   @Test
   //@InSequence(1)
   public void createRecipeTest(){
            
//        ingredientList.add(new Ingredient("voda", "", "20 dl", true));
//        ingredientList.add(new Ingredient("zemiaky", "take gulate najlepsie", "4", true));
//        ingredientList.add(new Ingredient("bryndza", "kvalitna", "40 dg", true));
//        recipe = new Recipe();
//        recipe.setName("Halusky");
//        recipe.setOneSentenceDescription("blablabla");
//        recipe.setDescription("blablablabla blabala");
//        recipe.setFoodCategory(FoodCategory.OTHER);
//        recipe.setOwner(user);
//        recipe.setIngredientList(ingredientList);
//        recipe.setPortions(2);
//        recipe.setPrice(100);
//        recipe.setStars(0);
//        recipe.setTime(1);
            User filip = new User("filip", "kiss", "filip", "filip@gmail.com", "filip", true);
            Recipe recipe = new Recipe("halusky", "nastruhaj zemiaky atd... ", filip, FoodCategory.OTHER, 4, 0, 213, 100, "velmi jednoduche halusky starej matere");

            assertNull(recipeDao.findRecipeByName(recipe.getName()));

       try {
           recipeDao.createRecipe(recipe);
       } catch (IllegalArgumentException e) {
           fail();
       }
       
       assertNotNull(recipeDao.findRecipeByName(recipe.getName()));
   }
  
   
   @Test
   public void editRecipeTest(){
       User tomas = new User("tomas", "plevko", "tom", "tomas@gmail.com", "tom", true);
       Recipe recipe = new Recipe("halusky", "nastruhaj zemiaky atd... ", tomas, FoodCategory.OTHER, 4, 0, 213, 100, "velmi jednoduche halusky starej matere");
       recipeDao.createRecipe(recipe);
       
       assertEquals(recipe.getName(), "halusky");
      
       recipe.setName("halusky so slaninou");
       
       try {
          recipeDao.editRecipe(recipe);
       } catch (IllegalArgumentException e) {
           fail();
       }
       
       assertEquals(recipe.getName(), "halusky so slaninou");
       
   }
   
   @Test
   public void deleteRecipeTest(){
       User tomas = new User("tomas", "plevko", "tom", "tomas@gmail.com", "tom", true);
       Recipe recipe = new Recipe("halusky", "nastruhaj zemiaky atd... ", tomas, FoodCategory.OTHER, 4, 0, 213, 100, "velmi jednoduche halusky starej matere");
       recipeDao.createRecipe(recipe);
       
       assertNotNull(recipeDao.findRecipeByName(recipe.getName()));
       
       try {
            recipeDao.removeRecipe(recipe);
       } catch (IllegalArgumentException e) {
           fail();
       }
       
       assertNull(recipeDao.findRecipeByName(recipe.getName()));
       
   }
           
   
   
   
   
}
