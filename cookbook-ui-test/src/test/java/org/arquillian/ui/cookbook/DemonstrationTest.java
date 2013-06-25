package org.arquillian.ui.cookbook;

import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thoughtworks.selenium.DefaultSelenium;
import java.io.File;
import org.jboss.shrinkwrap.api.Archive;

@RunWith(Arquillian.class)
public class DemonstrationTest {

    @Deployment(testable = false)
    public static Archive<?> createDeployment() {

        return ShrinkWrap.createFromZipFile(WebArchive.class, new File("../target/cookbook-webapp.war"));

    }
    
    @Drone
    DefaultSelenium browser;
    @ArquillianResource
    URL deploymentUrl;

    @Test
    public void functionalityExample() throws InterruptedException {
        String ingName = "//td[2]/input";
        String description = "//tr[2]/td[2]/input";
        String quantity = "//tr[3]/td[2]/input";
        String addIng = "//input[3]";
        
        String recipeName = "//form[2]/table/tbody/tr/td[2]/input";
        String recipeDescription = "//textarea";
        String oneSentenceDesc = "//tr[3]/td[2]/textarea";
        String time = "//form[2]/table/tbody/tr[4]/td[2]/input";
        String portions = "//tr[5]/td[2]/input";
        String  price = "//tr[6]/td[2]/input";
        
        browser.setSpeed("1500");

        browser.open(deploymentUrl + "index.jsf");
        
        browser.waitForPageToLoad("15000");

        browser.click("link=Register");

        Thread.sleep(2000);

        browser.type("id=registerUse:nick", "demo");
        browser.type("id=registerUse:firstName", "John");
        browser.type("id=registerUse:surname", "Smith");
        browser.type("id=registerUse:email", "john");
        browser.type("id=registerUse:password", "demo");
        browser.type("id=registerUse:confirmPassword", "demo");
        browser.click("id=registerUse:register");

        browser.type("id=registerUse:email", "john@demo.com");
        browser.type("id=registerUse:password", "demo");
        browser.type("id=registerUse:confirmPassword", "demo");
        browser.click("id=registerUse:register");

        browser.click("link=Login");
        browser.type("id=loginForm:username","demo");
        browser.type("id=loginForm:password","demo");
        browser.click("//input[2]");
        
        browser.click("link=New recipe");
        browser.type(ingName,"tortila");
        browser.type(quantity,"1ks");
        browser.click(addIng);
        
        browser.type(description,"fajná");
        browser.click(addIng);
        
         browser.setSpeed("200");

        browser.type(ingName,"mozarella");
        browser.type(description,"chutná");
        browser.type(quantity,"50g");
        browser.click(addIng);
        
        browser.type(ingName,"paradajky");
        browser.type(description,"červené");
        browser.type(quantity,"4");
        browser.click(addIng);
        
        browser.type(ingName,"bazalka");
        browser.type(description,"sušená");
        browser.type(quantity,"1");
        browser.click(addIng);
        
        browser.type(ingName,"olej");
        browser.type(description,"olivový");
        browser.type(quantity,"1");
        browser.click(addIng);
        
        browser.type(ingName,"cesnak");
        browser.type(description,"štipľavý");
        browser.type(quantity,"1");
        browser.click(addIng);
        
        browser.type(ingName,"soľ");
        browser.type(description,"slaná");
        browser.type(quantity,"kúsok");
        browser.click(addIng);
        
        browser.setSpeed("1500");
        
        browser.type(recipeName,"Tortillová pizza");
        browser.type(recipeDescription,"Najprv si pripravíme zmes, ktorá pôjde na tortillu. Dve malé cherry paradajky zbavíme šupky sparením. "
                + "Následne na to ich rozmixujeme s lyžičkou olivového oleja, štipkou soli, strúčikom cesnaku a bazalkou (môže sa tam pridať "
                + "korenie/bylinka podľa vlastného výberu, ale ja mám slabosť pre bazalku). Zmesou potrieme tortillu vopred pripravenú na plechu. "
                + "Na potrenú tortillu naukladáme zvyšné nakrájané cherry paradajky a mozarellu, dochutíme ďalšou dávkou bazalky."
                + " Takto pripravenú tortilu pečieme približne 10 minút pri 200° C. Po vybratí z rúry ju môžeme preložiť na polovicu, ľahšie sa bude jesť. "
                + "Dobrú chuť. :)");
        browser.type(oneSentenceDesc,"Chutná pizza za pár minút");
        browser.type(time,"20");
        browser.type(portions,"1");
        browser.type(price,"100");
        
        Thread.sleep(2000);
        browser.click("//select");
        browser.click("//option[@value='OTHER']");
        Thread.sleep(4000);
        browser.click("//form[2]/input[2]");
        
       //editRecipe
        browser.click("link=my recipes");
        browser.click("//input");
        Thread.sleep(5000);

        browser.type(price,"110");
        Thread.sleep(3000);

        browser.click("//form[2]/input[2]");
        Thread.sleep(4000);
        
        browser.click("link=Log Out");
        Thread.sleep(2000);

        browser.click("//input[2]");
        
        browser.click("link=Login");
        browser.type("id=loginForm:username","admin");
        browser.type("id=loginForm:password","admin");
        browser.click("//input[2]");
        
        browser.click("link=all users");
        Thread.sleep(4000);

        browser.click("//tr[5]/td[6]/input");
        browser.click("link=Log Out");
        browser.click("//input[2]");
        
        browser.click("link=Home");
        browser.click("id=find_form:find_button");
        Thread.sleep(5000);
        
        browser.type("id=find_form:search","hal");
        browser.click("id=find_form:find_button");
        Thread.sleep(5000);
        
        browser.type("id=find_form:search","cesnekova polievka");
        browser.click("id=find_form:find_button");
        Thread.sleep(3000);
        
        browser.type("id=find_form:search","kolac");
        browser.click("id=find_form:find_button");
        Thread.sleep(5000);  
        
        browser.type("id=find_form:search","mlieko, cukor, jablko, jahody, muka");
        browser.click("id=find_form:find_button");
        Thread.sleep(5000);  
        
        browser.type("id=find_form:search","zemiaky, cibula, cesnak, syr");
        browser.click("id=find_form:find_button");
        Thread.sleep(5000);
        
        browser.open(deploymentUrl + "user/insertRecipe.jsf");
        Thread.sleep(7000);


    }
}
