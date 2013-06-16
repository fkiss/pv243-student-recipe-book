package cz.muni.fi.pv243.cookbook.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cz.muni.fi.pv243.cookbook.model.User;

@Stateless
public class GetAllUsersController {

    @PersistenceContext
    private EntityManager manager;

    @Produces
    @Model
    public List<User> getAllUsers () {
    	return manager.createQuery("select user from User user order by user.surname", User.class).getResultList();
    }
}
