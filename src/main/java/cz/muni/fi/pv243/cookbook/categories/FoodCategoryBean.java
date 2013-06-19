package cz.muni.fi.pv243.cookbook.categories;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class FoodCategoryBean {

	public FoodCategory[] getFoodCategoryValues() {

		FoodCategory[] categories = FoodCategory.values();

		return categories;
	}
}

