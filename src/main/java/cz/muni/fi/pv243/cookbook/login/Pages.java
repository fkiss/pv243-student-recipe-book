package cz.muni.fi.pv243.cookbook.login;

import org.jboss.seam.faces.event.PhaseIdType;
import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.rewrite.UrlMapping;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.security.RestrictAtPhase;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;
import org.jboss.seam.security.annotations.LoggedIn;

@ViewConfig

public interface Pages {

    static enum Pages1 {

		@ViewPattern("/index.xhtml")
		INDEX,

        @ViewPattern("/admin/*")
        @Admin
        @LoginView("/userLogin.xhtml")
        @AccessDeniedView("/userLogin.xhtml")
		@RestrictAtPhase({ PhaseIdType.RESTORE_VIEW,
			PhaseIdType.INVOKE_APPLICATION })
        ADMIN_ACTIONS,

		@ViewPattern("/user/*")
		@LoginView("/userLogin.xhtml")
		@AccessDeniedView("/userLogin.xhtml")
		@User
		@RestrictAtPhase({ PhaseIdType.RESTORE_VIEW,
				PhaseIdType.INVOKE_APPLICATION })
		USER_PROFILE,

        @FacesRedirect
        @ViewPattern("/*")
        @AccessDeniedView("/userLogin.xhtml")
        ALL;

    }
}