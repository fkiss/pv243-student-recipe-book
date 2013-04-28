package cz.muni.fi.pv243.studentRecipeBook.daoImpl;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import cz.muni.fi.pv243.studentRecipeBook.dao.UserDao;
import cz.muni.fi.pv243.studentRecipeBook.entities.User;
import cz.muni.fi.pv243.studentRecipeBook.util.ShaEncoder;

@Stateless
@LocalBean
public class UserDaoImpl implements UserDao{

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

        Query q = em.createQuery("SELECT u FROM cz.muni.fi.pv243.studentRecipeBook.entities.User u "
                + "WHERE u.nick = :nick");
        q.setParameter("nickName", user.getNick());

        if (!q.getResultList().isEmpty()) {
            throw new IllegalArgumentException("Uzivatel s nickom: " + user.getNick()
                    + " uz existuje");
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

        try {
            User u = em.find(User.class, user.getId());
            u.setNick(user.getNick());
            u.setFirstName(user.getFistName());
            u.setSurname(user.getSurname());
            u.setUserRole(user.getUserRole());
            String digest = ShaEncoder.hash(user.getPasswd());
            u.setPasswd(digest);
            em.merge(u);
        } finally {
            em.close();
        }		
	}

	public void deleteUser(User user) {
		if (user == null) {
            throw new IllegalArgumentException("Argument can not be null");
        }

        if (user.getId() == null) {
            throw new IllegalArgumentException("Id nemoze byt null, uzivatel nieje v databazi");
        }

        try {
            User u = em.find(User.class, user.getId());
            em.remove(u);
        } finally {
            em.close();
        }		
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

	
}
