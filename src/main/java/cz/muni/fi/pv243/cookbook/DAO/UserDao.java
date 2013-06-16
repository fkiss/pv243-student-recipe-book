package cz.muni.fi.pv243.cookbook.DAO;

import cz.muni.fi.pv243.cookbook.model.User;

public interface UserDao {

    public void createUser(User user);
    
    public void removeUser(User user);
    
    public void editUser(User user);
    
    public User findUserByID(Long id);
    
    public User findUserByNick(String nick);
    
    public User findUserByEmail(String email);
}
