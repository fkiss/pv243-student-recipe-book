package cz.muni.fi.pv243.cookbook.logging;

import org.jboss.solder.logging.Log;
import org.jboss.solder.logging.MessageLogger;
import org.jboss.solder.messages.Message;

/**
 *
 * @author fkiss
 */
@MessageLogger
public interface UserLogger {
    
    @Log @Message("User %s %s created.")
    void created(String firstName, String Surname);
       
    @Log @Message("User %s %s edited.")
    void edited(String firstName, String Surname);
    
    @Log @Message("User %s %s found.")
    void found(String firstName, String Surname);
   
    @Log @Message("User %s %s deleted.")
    void deleted(String firstName, String Surname);
}
