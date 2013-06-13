package cz.muni.fi.pv243.studentRecipeBook.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class User implements Serializable{

	private static final long serialVersionUID = 6960908576823114213L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false,unique=true)
	private String nick;
	
	@Column(nullable=false)
	private String firstName;
	
	@Column(nullable=false)
	private String surname;

    @NotNull
    @NotEmpty
    @Email
    @Column(unique=true)
	private String email;

    @NotEmpty
    private Date date;
    
    @NotEmpty
    private boolean isAdmin = false;
	
	@Column(nullable=false)
	private String passwd;

	public User() {
	}

	public User(String firstName, String surname, String nick, String email, String password, boolean isAdmin) {
		this.firstName = firstName;
		this.surname = surname;
		this.nick = nick;
		this.email = email;
		this.passwd = passwd;
		this.isAdmin = isAdmin;
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

	public String getFirstName() {
		return firstName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getFistName() {
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

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	/**
	 * generates the time, the user registrated
	 */
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
	
	public enum UserRole {
	    ROLE_GUEST, ROLE_USER, ROLE_ADMIN
	}
}


