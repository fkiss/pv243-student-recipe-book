package cz.muni.fi.pv243.cookbook.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.login.Login;
import cz.muni.fi.pv243.cookbook.model.User;

@Named
@ConversationScoped
public class UpdateUserController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	Conversation conversation;

	@Inject
	private UserDao userDao;

	@Produces
	private User user;

	@Inject
	private Login login;

	public void retrieve() {

		if (conversation.isTransient()) {
			conversation.begin();
		}

		Long currentId = login.getCurrentUser().getId();
		user = userDao.findUserByID(currentId);
	}

	public User getUser() {
		return user;
	}

	public void update() {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {

			if (login.isLoggedIn()) {
				userDao.editUser(user);
				String message = "account successfully updated";
				facesContext.addMessage(null, new FacesMessage(message));

				conversation.end();

			} else {

				facesContext
						.addMessage(null, new FacesMessage("please log in"));

			}

		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Update unsuccessful");
			facesContext.addMessage(null, m);
			conversation.end();
		}
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Registration failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}
}
