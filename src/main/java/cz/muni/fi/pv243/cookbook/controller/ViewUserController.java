package cz.muni.fi.pv243.cookbook.controller;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.model.User;

@Named
@RequestScoped
public class ViewUserController {

	@Inject
	private UserDao userDao;
	
	@Produces
	private User user;
	
	@Inject
	private FacesContext facesContext;
	
	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void retrieve() {

			user = userDao.findUserByID(id);
	}
	
	public User getUser() {
		return user;
	}
}
