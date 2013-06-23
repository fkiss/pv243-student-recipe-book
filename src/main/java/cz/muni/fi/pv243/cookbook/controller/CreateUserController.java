package cz.muni.fi.pv243.cookbook.controller;

import java.io.Serializable;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.security.Identity;

import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.login.Login;
import cz.muni.fi.pv243.cookbook.model.User;

@Named
@ConversationScoped
public class CreateUserController implements Serializable{

	private static final long serialVersionUID = 7642386483268721L;

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private UserDao userDao;

	@Inject
	Conversation conversation;
	
	@Produces
	private User user = new User();
	
	@Inject
	private Identity identity;

	public void retrieve() {

		if (conversation.isTransient()) {
			conversation.begin();
		}

		user = userDao.findUserByID(Long.parseLong(identity.getUser().getId()));
	}

	public User getUser() {
		return user;
	}
	
	public void create() {
        try {
            userDao.createUser(user);
            String message = "account successfully created";
            facesContext.addMessage(null, new FacesMessage(message));
        } catch (Exception e) {
        	
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getLocalizedMessage(), "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

	public void update() {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {

			userDao.editUser(user);
			String message = "account successfully updated";
			facesContext.addMessage(null, new FacesMessage(message));

			conversation.end();

		} catch (Exception e) {
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					e.getLocalizedMessage(), "Update unsuccessful");
			facesContext.addMessage(null, m);
			conversation.end();
		}
	}


}
