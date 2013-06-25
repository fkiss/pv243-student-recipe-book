package cz.muni.fi.pv243.cookbook.categories;


public enum FoodCategory {

	MEAT("meat"), APPETIZER("appetizer"), OTHER("other"), SOUP("soup"), DESSERT("dessert"), HEALTHY("healthy");
	
    private final String label;

    private FoodCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
