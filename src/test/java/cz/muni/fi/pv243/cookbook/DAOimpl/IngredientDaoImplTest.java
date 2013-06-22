package cz.muni.fi.pv243.cookbook.DAOimpl;

import cz.muni.fi.pv243.cookbook.DAO.IngredientDao;
import cz.muni.fi.pv243.cookbook.categories.FoodCategory;
import cz.muni.fi.pv243.cookbook.model.Ingredient;
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
public class IngredientDaoImplTest
{
   @Inject
   private IngredientDao ingredientDao;
   
   
   @Deployment
   public static Archive<?> createTestArchive()
   {
      return ShrinkWrap.create(WebArchive.class, "test.war")
            .addClass(FoodCategory.class)
            .addPackages(true, IngredientDaoImpl.class.getPackage(), Ingredient.class.getPackage(), IngredientDao.class.getPackage())
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsWebInfResource("test-ds.xml");
   }

   @Test
   public void testIsDeployed()
   {
      Assert.assertNotNull(ingredientDao);
   }
   
   @Test
   public void createIngredientTest(){
            

       Ingredient voda = new Ingredient("voda", "tekuta", "20 dl", true);

       try {
                  assertNull(ingredientDao.findIngredientById(voda.getId()));
       } catch (Exception e) {
           assertTrue(true);
       }


       try {
           ingredientDao.createIngredient(voda);
       } catch (Exception e) {
           fail();
       }

       assertNotNull(ingredientDao.findIngredientById(voda.getId()));

   }

   
   @Test
   public void editIngredientTest(){
        
       Ingredient voda = new Ingredient("voda", "tekuta", "20 dl", true);

       ingredientDao.createIngredient(voda);
       
       assertEquals(voda.getName(), "voda");
      
       voda.setName("olej");
       
       try {
          ingredientDao.editIngredient(voda);
       } catch (IllegalArgumentException e) {
           fail();
       }
       
       assertEquals(voda.getName(), "olej");
       
   }
   
   @Test
   public void deleteIngredientTest(){
       
       Ingredient voda = new Ingredient("voda", "tekuta", "20 dl", true);

       ingredientDao.createIngredient(voda);
       
       assertNotNull(ingredientDao.findIngredientById(voda.getId()));
       
       try {
            ingredientDao.removeIngredient(voda);
       } catch (IllegalArgumentException e) {
           fail();
       }
       
       assertNull(ingredientDao.findIngredientById(voda.getId()));
       
   }
           
   
   
   
   
}
