package cz.muni.fi.pv243.cookbook.model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 132131235423L;

	@Id
    @GeneratedValue
	private Long id;

    @NotNull
    @NotEmpty
	private String firstName;
    
    @NotNull
    @NotEmpty
	private String surname;
	
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 25)
    @Column(unique=true)
	private String nick;
    
    @NotNull
    @NotEmpty
	private String password;
	
    @NotNull
    @NotEmpty
    @Email
    @Column(unique=true)
	private String email;
    
    private Date date;
    
    @NotNull
    private boolean isAdmin;

	public User() {
	}

	public User(String firstName, String surname, String nick, String email, String password, boolean isAdmin) {
		this.firstName = firstName;
		this.surname = surname;
		this.nick = nick;
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User[ id=" + id + ", nick=" + nick + ", name=" + firstName
                + ", surname=" + surname + "]";
    }
}
