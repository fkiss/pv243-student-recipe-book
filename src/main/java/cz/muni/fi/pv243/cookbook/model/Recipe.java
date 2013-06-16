package cz.muni.fi.pv243.cookbook.model;

import cz.muni.fi.pv243.cookbook.categories.FoodCategory;
import cz.muni.fi.pv243.cookbook.model.User;

import java.io.Serializable;
import java.lang.Long;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Recipe
 * 
 */
@Entity
public class Recipe implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Column(unique = true)
	private String name;

	@NotNull
	private String description;

	@NotNull
	private FoodCategory foodCategory;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_id", referencedColumnName = "id")
	private User owner;

	@Min(0)
	private Integer portions;

	@Min(0)
	private Integer stars;

	@Min(0)
	private Integer time;

	@OneToMany(fetch = FetchType.EAGER)
	private List<Ingredient> ingredientList = new ArrayList<Ingredient>();

	private Date date;

	//TODO : dorobit tu cenu
	@Min(0)
	private Integer price;

	public Recipe() {
	}

	public Recipe(String name, String description, User owner,
			FoodCategory category, Integer portions, Integer stars, Integer time) {

		this.name = name;
		this.description = description;
		this.owner = owner;
		this.foodCategory = category;
		this.portions = portions;
		this.stars = stars;
		this.time = time;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getOwner() {
		return this.owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Integer getStars() {
		return this.stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public Integer getTime() {
		return this.time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public List<Ingredient> getIngredientList() {
		return ingredientList;
	}

	public void setIngredientList(List<Ingredient> ingredientList) {
		this.ingredientList = ingredientList;
	}

	public Integer getPortions() {
		return portions;
	}

	public void setPortions(Integer portions) {
		this.portions = portions;
	}

	public FoodCategory getFoodCategory() {
		return foodCategory;
	}

	public void setFoodCategory(FoodCategory category) {
		this.foodCategory = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@PrePersist
	protected void onCreate() {
		date = new Date();
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}
	
	

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Recipe)) {
			return false;
		}
		Recipe other = (Recipe) object;
		if ((this.id == null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "[Recept: " + name + ", Autor: " + owner + ", Prisady: <"
				+ ingredientList + " >]";
	}
}
