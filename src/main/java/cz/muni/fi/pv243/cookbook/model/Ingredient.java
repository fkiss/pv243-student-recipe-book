package cz.muni.fi.pv243.cookbook.model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Entity implementation class for Entity: Ingredient
 *
 */
@Entity
public class Ingredient implements Serializable {

	@Id
    @GeneratedValue
	private Long id;

    @NotNull
	private String name;

    @NotNull
	private String description;

    @NotNull
	private String quantity;

    @NotNull
	private boolean obligatory;

	private static final long serialVersionUID = 1L;

	public Ingredient() {
	}   
	
	public Ingredient(String name, String description, String quantity, boolean obligatory) {
		
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.obligatory = obligatory;
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
	
	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}   
	
	public boolean getObligatory() {
		return this.obligatory;
	}

	public void setObligatory(boolean obligatory) {
		this.obligatory = obligatory;
	}

	// this equals can't be used, if we use with not persistent entities
	
//	@Override
//	public int hashCode() {
//		int hash = 0;
//		hash += (id != null ? id.hashCode() : 0);
//		return hash;
//	}
//
//	@Override
//	public boolean equals(Object object) {
//		if (!(object instanceof Ingredient)) {
//			return false;
//		}
//		Ingredient other = (Ingredient) object;
//		if ((this.id == null) || (this.id != null && !this.id.equals(other.id))) {
//			return false;
//		}
//		return true;
//	}

	@Override
	public String toString() {
		return (description.isEmpty()) ? name + ", " + quantity : name + ", "
				+ quantity + ", " + description;
	}
}
