package cz.muni.fi.pv243.cookbook;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.cookbook.model.Recipe;

@Singleton
@ApplicationScoped
class TitlePage {

	@PersistenceContext
	private EntityManager manager;

	private Recipe recipeForTheDay;

	private Timer timer = new Timer();

	@PostConstruct
	private void generateRecipeOfTheDay() {
		
		timer.schedule(dailyTask, 01, 1000 * 60 * 60 * 24);
	}

	TimerTask dailyTask = new TimerTask() {
		@Override
		public void run() {

			Query query = manager
					.createQuery("SELECT recipe FROM Recipe recipe ORDER BY random()");
			query.setMaxResults(1);
			recipeForTheDay = (Recipe) query.getSingleResult();
		}
	};

	//TODO : mozno pouzit iny timer... vraj je nejaky zabudovany priamo v EJB, tak kukni ten...
	// 		http://docs.oracle.com/javaee/6/tutorial/doc/bnboy.html
	
	@Named
	@Produces
	public Recipe getRecipeForTheDay() {

		return recipeForTheDay;
	}
}
