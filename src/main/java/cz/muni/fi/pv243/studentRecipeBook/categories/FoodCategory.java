package cz.muni.fi.pv243.studentRecipeBook.categories;

/**
 * Food categories
 *
 */
public enum FoodCategory {

	FLESH("flesh"), APPETIZER("appetizer"), OTHER("other"), SOUP("soup"), DESSERT("desert"), HEALTHY("healthy");
	

    private final String label;

    private FoodCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}