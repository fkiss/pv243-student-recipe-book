package cz.muni.fi.pv243.cookbook.login;

import java.io.Serializable;

import org.jboss.annotation.ejb.Service;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

@Service
public class Authorization implements Serializable{

	private static final long serialVersionUID = 1329890238401L;

	@Secures
	@Admin
	public static boolean isAdmin(Identity identity) {

		if (!identity.isLoggedIn()) {
			return false;
		}
		
		return identity.getUser().getKey().equals(UserRole.ADMIN.toString());
	}

	@Secures
	@User
	public static boolean isUser(Identity identity) {

		if (!identity.isLoggedIn()) {
			return false;
		}

        return true;
	}
}