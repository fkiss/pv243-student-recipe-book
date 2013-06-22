package cz.muni.fi.pv243.cookbook.DAOimpl;

import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.categories.FoodCategory;
import cz.muni.fi.pv243.cookbook.model.Ingredient;
import cz.muni.fi.pv243.cookbook.model.User;
import cz.muni.fi.pv243.cookbook.util.ShaEncoder;
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
public class UserDaoImplTest
{
   @Inject
   private UserDao userDao;
   
   
   @Deployment
   public static Archive<?> createTestArchive()
   {
      return ShrinkWrap.create(WebArchive.class, "test.war")
            .addClass(FoodCategory.class)
            .addPackages(true, UserDaoImpl.class.getPackage(), User.class.getPackage(), UserDao.class.getPackage(), ShaEncoder.class.getPackage())
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsWebInfResource("test-ds.xml");
   }

   @Test
   public void testIsDeployed()
   {
      Assert.assertNotNull(userDao);
   }
   
   @Test
   public void createUserTest(){
            
       User filip2 = new User("filip2", "kiss", "filip2", "filip2@gmial.com", "filip2", false);

       try {
            assertNull(userDao.findUserByID(filip2.getId()));
       } catch (Exception e) {
           assertTrue(true);
       }

       try {
           userDao.createUser(filip2);
       } catch (IllegalArgumentException e) {
           fail();
       }

       assertNotNull(userDao.findUserByID(filip2.getId()));

   }

   
   @Test
   public void editUserTest(){
        
       User filip3 = new User("filip3", "kiss3", "filip3", "filip3@gmial.com", "filip3", false);

       userDao.createUser(filip3);
       
       assertEquals(filip3.getFirstName(), "filip3");
      
       filip3.setFirstName("fero");
       
       try {
          userDao.editUser(filip3);
       } catch (Exception e) {
           fail();
       }
       
       assertEquals(filip3.getFirstName(), "fero");
       
   }
   
   @Test
   public void deleteUserTest(){
       
       User filip = new User("filip", "kiss", "filip", "filip@gmial.com", "filip", false);

       userDao.createUser(filip);
       
       assertNotNull(userDao.findUserByID(filip.getId()));
       
       try {
            userDao.removeUser(filip);
       } catch (Exception e) {
           fail();
       }
       
       assertNull(userDao.findUserByID(filip.getId()));
       
   }
           
   
   
   
   
}
