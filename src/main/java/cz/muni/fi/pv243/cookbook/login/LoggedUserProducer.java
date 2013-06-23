package cz.muni.fi.pv243.cookbook.login;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.seam.security.Identity;
import org.jboss.solder.unwraps.Unwraps;

import cz.muni.fi.pv243.cookbook.DAO.UserDao;
import cz.muni.fi.pv243.cookbook.model.User;

public class LoggedUserProducer implements Serializable {

	private static final long serialVersionUID = 4603063114429231508L;
	
	@Inject
    private Identity identity;
	
    @Inject
    private UserDao userDao;

    public User produceLoggedUser() {
        if (!identity.isLoggedIn()) {
            return null;
        }
        return userDao.findUserByID(Long.parseLong(identity.getUser().getId()));
    }
}

