package cz.muni.fi.pv243.cookbook.DAOimpl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.model.User;
import cz.muni.fi.pv243.cookbook.util.ShaEncoder;

@Stateless
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public void createUser(User user) {

		if (user == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		if (user.getId() != null) {
			throw new IllegalArgumentException("User ID already exists");
		}

		if (!(findUserByNick(user.getNick()) == null)) {
			throw new IllegalArgumentException("User with nick: "
					+ user.getNick() + " already exists");
		}

		if (!(findUserByEmail(user.getEmail()) == null)) {
			throw new IllegalArgumentException("User with email: "
					+ user.getEmail() + " already exists");
		}

		String digest = ShaEncoder.hash(user.getPassword());
		user.setPassword(digest);
		
		manager.persist(user);
	}

	@Override
	public void removeUser(User user) {

		if (user == null) {
			throw new IllegalArgumentException("Argument can not be null");
		}

		if (user.getId() == null) {
			throw new IllegalArgumentException(
					"Id cannot be null, user is not in the DB");
		}

		User userToDelete = manager.find(User.class, user.getId());
		manager.remove(userToDelete);
	}

	@Override
	public User findUserByID(Long id) {

		return manager.find(User.class, id);
	}

	@Override
	public User findUserByNick(String nick) {
		try {
			Query query = manager.createQuery(
					"select u from User u where u.nick=:nick").setParameter(
					"nick", nick);
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public User findUserByEmail(String email) {
		try {
			Query query = manager.createQuery(
					"select u from User u where u.email=:email").setParameter(
					"email", email);
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void editUser(User user) {

		if (user == null) {
			throw new IllegalArgumentException("Argument is null");
		}

		User u = manager.find(User.class, user.getId());
		u.setNick(user.getNick());
		u.setFirstName(user.getFirstName());
		u.setSurname(user.getSurname());
		u.setEmail(user.getEmail());
		String digest = ShaEncoder.hash(user.getPassword());
		u.setPassword(digest);
		manager.merge(u);
	}
}