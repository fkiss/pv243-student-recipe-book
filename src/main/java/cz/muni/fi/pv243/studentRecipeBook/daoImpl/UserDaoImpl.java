package cz.muni.fi.pv243.studentRecipeBook.daoImpl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.studentRecipeBook.dao.UserDao;
import cz.muni.fi.pv243.studentRecipeBook.entities.User;
import cz.muni.fi.pv243.studentRecipeBook.util.ShaEncoder;

@Stateless
@LocalBean
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager em;

	public void createUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("Argument je null");
		}

		if (user.getId() != null) {
			throw new IllegalArgumentException("Uzivatelove id je uz zadane");
		}

		if (em == null) {
			System.out.println("EntityManager je null");
		}

		Query q = em
				.createQuery("SELECT u FROM cz.muni.fi.pv243.studentRecipeBook.entities.User u "
						+ "WHERE u.nick = :nick");
		q.setParameter("nickName", user.getNick());

		if (!q.getResultList().isEmpty()) {
			throw new IllegalArgumentException("Uzivatel s nickom: "
					+ user.getNick() + " uz existuje");
		}

		try {
			String digest = ShaEncoder.hash(user.getPasswd());
			user.setPasswd(digest);
			em.persist(user);
		} finally {
			em.close();
		}
	}

	public void updateUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("Argument je null");
		}

		User u = em.find(User.class, user.getId());
		u.setNick(user.getNick());
		u.setFirstName(user.getFistName());
		u.setSurname(user.getSurname());
		u.setAdmin(user.isAdmin());
		u.setEmail(user.getEmail());
		
		String digest = ShaEncoder.hash(user.getPasswd());
		u.setPasswd(digest);
		em.merge(u);
	}

	public void deleteUser(User user) {
		if (user == null) {
			throw new IllegalArgumentException("Argument can not be null");
		}

		if (user.getId() == null) {
			throw new IllegalArgumentException(
					"Id nemoze byt null, uzivatel nieje v databazi");
		}

		User u = em.find(User.class, user.getId());
		em.remove(u);
	}

	public User retrieveUser(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Argument je null");
		}

		if (id.longValue() < 0) {
			throw new IllegalArgumentException("Id nemoze byt zaporne");
		}

		try {
			return em.find(User.class, id);
		} finally {
			em.close();
		}
	}

	public User findUserByID(Long id) {

		try {
			return em.find(User.class, id);
		} catch (NoResultException e) {
			return null;
		}
	}

	public User findUserByNick(String nick) {
		try {
			Query query = em.createQuery(
					"select u from User u where u.nick=:nick").setParameter(
					"nick", nick);
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public User findUserByEmail(String email) {
		try {
			Query query = em.createQuery(
					"select u from User u where u.email=:email").setParameter(
					"email", email);
			return (User) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
