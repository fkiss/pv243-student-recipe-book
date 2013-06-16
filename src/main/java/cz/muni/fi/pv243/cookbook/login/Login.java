package cz.muni.fi.pv243.cookbook.login;

import java.io.Serializable;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.model.User;
import cz.muni.fi.pv243.cookbook.util.ShaEncoder;

@Named
@SessionScoped
@Stateful
public class Login implements Serializable {

	private static final long serialVersionUID = 121398231123L;

	@Inject
	private Credentials credentials;

	@Inject
	private UserDao userDAO;

	private User currentUser;

	public void login() throws Exception {

		User user = userDAO.findUserByNick(credentials.getNick());

		String hash = ShaEncoder.hash(credentials.getPassword());
		
		if (user != null
				&& user.getPassword().equals(hash)) {
			this.currentUser = user;

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Welcome, " + currentUser.getNick()));
		} else {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Loggin not sucessfull"));
		}
	}

	public void logout() {

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage("Goodbye, " + currentUser.getNick()));
		currentUser = null;
	}

	@Produces
	public boolean isLoggedIn() {

		return currentUser != null;
	}

	// TODO : LoggedIn - asi to bude treba prerobit...
	public User getCurrentUser() {

		return currentUser;

	}

}
