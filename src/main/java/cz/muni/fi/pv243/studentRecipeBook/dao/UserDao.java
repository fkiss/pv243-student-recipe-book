package cz.muni.fi.pv243.studentRecipeBook.dao;

import cz.muni.fi.pv243.studentRecipeBook.entities.User;

public interface UserDao {

	public void createUser(User user);
	
	public void updateUser(User user);
	
	public void deleteUser(User user);
	
	public User retrieveUser(Long id);
}
