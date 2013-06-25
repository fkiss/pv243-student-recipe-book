package cz.muni.fi.pv243.cookbook.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pv243.cookbook.DAO.IngredientDao;
import cz.muni.fi.pv243.cookbook.DAO.RecipeDao;
import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.categories.FoodCategory;
import cz.muni.fi.pv243.cookbook.model.Ingredient;
import cz.muni.fi.pv243.cookbook.model.Recipe;
import cz.muni.fi.pv243.cookbook.model.User;

@Singleton
@Startup
public class DemoInitialize {

	@PersistenceContext
	private EntityManager manager;

	@Inject
	private UserDao userDao;

	@Inject
	private RecipeDao recipeDao;

	@Inject
	private IngredientDao ingredientDao;

	@PostConstruct
	public void initialize() {

		userDao.createUser(new User("filip", "kiss", "filip",
				"filip@gmial.com", "filip", false));
		userDao.createUser(new User("marek", "tuharsky", "marek",
				"tuharsky@gmail.com", "marek", false));
		userDao.createUser(new User("roman", "romanovic", "roman",
				"roman@gmail.com", "roman", false));
		userDao.createUser(new User("admin", "admin", "admin",
				"admin@admin.admin", "admin", true));

		User tomas = userDao.findUserByNick("admin");
		User filip = userDao.findUserByNick("filip");
		User roman = userDao.findUserByNick("roman");

		recipeDao
				.createRecipe(new Recipe(
						"halusky",
						"Syrové brambory nastrouháme najemno a vymačkáme z nich vodu. Vodu necháme ustát "
								+ "a usazený škrob dáme do brambor, přidáme smíchané mouky a vypracujeme tužší těsto. "
								+ "Těsto protlačíme halušníkem do vroucí osolené vody. Halušky promícháme, aby "
								+ "se nepřilepily ke dnu hrnce, jakmile halušky vyplavou na povrch, jsou hotové. Halušky "
								+ "podáváme s kyselým zelím, brynzou, tvarohem, slaninou nebo škvarky.",
						tomas, FoodCategory.OTHER, 4, 0, 45, 150,
						"Klasické slovenské halušky"));

		recipeDao
				.createRecipe(new Recipe(
						"cesnekova polievka",
						"Menší cibuli a česnek očistíme. Cibuli nasekáme najemno, česnek také, nebo jej nakrájíme na "
								+ "plátky či prolisujeme. Záleží, jak má kdo rád. Brambory oloupeme a nakrájíme na kostičky. V hrnci "
								+ "rozehřejeme sádlo a zesklovatíme cibuli. Přidáme česnek, krátce orestujeme a zalijeme "
								+ "horkou vodou. Přidáme brambory, kostku bujónu (masový nebo zeleninový) a polévku osolíme, opepříme a "
								+ "přidáme drcený kmín. Česnečku vaříme do změknutí brambor. Ke konci přidáme do česnečky "
								+ "drhnutou majoránku. Pro silnější česnekový efekt můžeme přidat další 3 stroužky česneku, tentokrát už "
								+ "prolisovaného. Horkou česnečku lijeme nejlépe do misek a posypeme upečenými kostičkami "
								+ "tvrdšího chleba a nastrouhaným sýrem. Hotovou česnečku se sýrem ihned podáváme.",
						filip, FoodCategory.SOUP, 4, 0, 110, 50,
						"Řádná česnečka, která postaví na nohy i mrtvolu"));
		
		recipeDao
				.createRecipe(new Recipe(
						"Gulas s bramborami",
						"Nakrájíme cibuli, oloupeme salám a nakrájíme na kolečka/půlkolečka/kostičky, brambory " + 
						"nakrájíme na menší kostky. Cibuli zpěníme na rozpáleném oleji spolu s česnekem, přihodíme " +
								"salám a chvilku opékáme. Poté přidáme červenou papriku, promícháme a zalijeme horkou "+ 
						"vodou (množství vody podle toho kolik chcete mít guláše). Přihodíme brambory, nasypeme " +
								"trochu drceného kmínu a vaříme do změknutí brambor. V hrnku/skleničce si"+
						" rozmícháme trochu hladké mouky s vodou (konzistence musí být kašovitá ale pořád tekutá)"+
								" a nalijeme do guláše aby se zahustil. Pak už jen nasypeme majoránku a guláš je" +
						" hotov.",
						filip, FoodCategory.SOUP, 4, 0, 100, 50,
						"klasický buřtguláš"));
		
		recipeDao.createRecipe(new Recipe("polievka",
				"zmiesame vodu, a pridame dalsie veci...", roman,
				FoodCategory.SOUP, 4, 0, 12, 50,
				"velmi jednoducha polievka, ktora zarucene chuti vyborne"));
		
		recipeDao
				.createRecipe(new Recipe("kolac jablkovy",
						"spravime cesto a dame piect do rury...", roman,
						FoodCategory.DESSERT, 4, 0, 12, 100,
						"kolac, ktroy si velmi rychlo sami pripravite aj v prostredi koleje"));

		recipeDao
				.createRecipe(new Recipe("kolac jahodovy",
						"spravime cesto a dame piect do rury...", roman,
						FoodCategory.DESSERT, 4, 0, 12, 100,
						"kolac, ktroy si velmi rychlo sami pripravite aj v prostredi koleje"));
		recipeDao
				.createRecipe(new Recipe("kolac jablkovo-jahodovy",
						"spravime cesto a dame piect do rury...", roman,
						FoodCategory.DESSERT, 4, 0, 12, 100,
						"kolac, ktroy si velmi rychlo sami pripravite aj v prostredi koleje"));
		recipeDao
				.createRecipe(new Recipe("kolac makovy",
						"spravime cesto a dame piect do rury...", roman,
						FoodCategory.DESSERT, 4, 0, 12, 100,
						"kolac, ktroy si velmi rychlo sami pripravite aj v prostredi koleje"));

		Recipe halusky = recipeDao.findRecipeByName("halusky");
		Recipe polievka = recipeDao.findRecipeByName("polievka");
		Recipe kolacJb = recipeDao.findRecipeByName("kolac jablkovy");
		Recipe kolacJh = recipeDao.findRecipeByName("kolac jahodovy");
		Recipe kolacJJ = recipeDao.findRecipeByName("kolac jablkovo-jahodovy");
		Recipe kolacM = recipeDao.findRecipeByName("kolac makovy");
		Recipe cesnecka = recipeDao.findRecipeByName("cesnekova polievka");
		Recipe burtgulas = recipeDao.findRecipeByName("Gulas s bramborami");

		List<Ingredient> haluskyList = new ArrayList<Ingredient>();

		halusky.setIngredientList(haluskyList);

		haluskyList.add(new Ingredient("zemiaky", "take gulate najlepsie", "4",
				true));
		haluskyList.add(new Ingredient("vajcia", "stredne velke", "3", true));
		haluskyList.add(new Ingredient("mlieko", "kravske", "2 dl", true));
		haluskyList.add(new Ingredient("slanina", "bravcova", "20 dg", false));

		polievka.getIngredientList().add(
				new Ingredient("pivo", "", "0.5 l", true));
		polievka.getIngredientList().add(
				new Ingredient("korenie", "", "20 dl", true));
		polievka.getIngredientList().add(
				new Ingredient("cukor", "", "20 dg", false));
		polievka.getIngredientList().add(
				new Ingredient("voda", "", "20 dl", true));

		kolacJb.getIngredientList().add(
				new Ingredient("mlieko", "najlepsie alpska", "20 dg", true));
		kolacJb.getIngredientList().add(
				new Ingredient("muka", "stredne velka", "1", true));
		kolacJb.getIngredientList().add(
				new Ingredient("cukor", "stredne velka", "5", false));
                                        kolacJb.getIngredientList().add(
				new Ingredient("jablko", "stredne velka", "5", false));

		kolacJh.getIngredientList().add(
				new Ingredient("mlieko", "najlepsie alpska", "2l", true));
		kolacJh.getIngredientList().add(
				new Ingredient("muka", "stredne velka", "1", true));
		kolacJh.getIngredientList().add(
				new Ingredient("cukor", "stredne velka", "5", false));
                                        kolacJh.getIngredientList().add(
				new Ingredient("jahody", "stredne velka", "5", false));

		kolacJJ.getIngredientList().add(
				new Ingredient("mlieko", "najlepsie alpska", "2l", true));
		kolacJJ.getIngredientList().add(
				new Ingredient("muka", "stredne velke", "1", true));
		kolacJJ.getIngredientList().add(
				new Ingredient("jablko", "stredne velke", "5", false));
                                        kolacJJ.getIngredientList().add(
				new Ingredient("jahody", "stredne velke", "5", false));

		kolacM.getIngredientList().add(
				new Ingredient("mlieko", "najlepsie alpske", "2l", true));
		kolacM.getIngredientList().add(
				new Ingredient("muka", "stredne velka", "1", true));
                                        kolacM.getIngredientList().add(
				new Ingredient("cukor", "krystalovy", "1", true));

		cesnecka.getIngredientList().add(
				new Ingredient("zemiaky", "gulate", "4 ks", true));
		cesnecka.getIngredientList().add(
				new Ingredient("cibula", "stredne velka", "1", true));
		cesnecka.getIngredientList().add(
				new Ingredient("cesnek", "velky", "2 palicky", true));
		cesnecka.getIngredientList().add(
				new Ingredient("bujon", "hovezi", "1 kocka", true));
		cesnecka.getIngredientList().add(
				new Ingredient("chlebove krutony", "na ozdobu", "hrst", false));
		cesnecka.getIngredientList().add(
				new Ingredient("syr", "na ozdobu", "15 dg", false));

		burtgulas.getIngredientList().add(
				new Ingredient("toceny salam", "", "250g", true));
		burtgulas.getIngredientList().add(
				new Ingredient("zemiaky", "väčšie", "4", true));
		burtgulas.getIngredientList().add(
				new Ingredient("cibula", "stredne velka", "1 Ks", true));
		burtgulas.getIngredientList().add(
				new Ingredient("mletá paprika", "sladka", "2 lyžice", true));
		burtgulas.getIngredientList().add(
				new Ingredient("majoranka", "", "", false));
		burtgulas.getIngredientList().add(
				new Ingredient("pepř", "", "", true));
		burtgulas.getIngredientList().add(
				new Ingredient("soľ", "", "", true));
		burtgulas.getIngredientList().add(
				new Ingredient("kmín", "", "", false));
		burtgulas.getIngredientList().add(
				new Ingredient("cesnak", "", "4 strúčiky", true));
		burtgulas.getIngredientList().add(
				new Ingredient("hladná múka", "", "", true));
		

		for (Ingredient ing : halusky.getIngredientList()) {
			ingredientDao.createIngredient(ing);
		}

		for (Ingredient ing : burtgulas.getIngredientList()) {
			ingredientDao.createIngredient(ing);
		}

		for (Ingredient ing : cesnecka.getIngredientList()) {
			ingredientDao.createIngredient(ing);
		}

		for (Ingredient ing : kolacJb.getIngredientList()) {
			ingredientDao.createIngredient(ing);
		}

		for (Ingredient ing : polievka.getIngredientList()) {
			ingredientDao.createIngredient(ing);
		}

		for (Ingredient ing : kolacJh.getIngredientList()) {
			ingredientDao.createIngredient(ing);
		}

		for (Ingredient ing : kolacJJ.getIngredientList()) {
			ingredientDao.createIngredient(ing);
		}

		for (Ingredient ing : kolacM.getIngredientList()) {
			ingredientDao.createIngredient(ing);
		}
	}
}