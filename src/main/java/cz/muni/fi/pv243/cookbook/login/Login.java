package cz.muni.fi.pv243.cookbook.login;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.picketlink.idm.impl.api.PasswordCredential;

import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;

import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.model.User;
import cz.muni.fi.pv243.cookbook.util.ShaEncoder;

public class Login extends BaseAuthenticator {

	@Inject
	private Credentials credentials;

	@Inject
	private UserDao userDAO;
	
	@Override
	public void authenticate() {

		final User user = userDAO.findUserByNick(credentials.getUsername());

		String hash = ShaEncoder.hash(((PasswordCredential) credentials
				.getCredential()).getValue());

		if (user != null && user.getPassword().equals(hash)) {

			UserRole role = UserRole.USER;
			
			if(user.isAdmin() == true) {
				role = UserRole.ADMIN;
			}
			
			final UserRole userRole = role;
			
			setUser(new org.picketlink.idm.api.User() {
				@Override
				public String getId() {
					return Long.toString(user.getId());
				}
				@Override
				public String getKey() {
					return userRole.toString();
				}
			});

			setStatus(AuthenticationStatus.SUCCESS);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Welcome, " + user.getNick()));

		} else {
			setStatus(AuthenticationStatus.FAILURE);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Loggin not sucessfull"));

			return;
		}
	}
}
