package cz.muni.fi.pv243.logging;

import org.jboss.solder.logging.Log;
import org.jboss.solder.logging.MessageLogger;
import org.jboss.solder.messages.Message;

/**
 *
 * @author fkiss
 */

@MessageLogger
public interface RecipeLogger {
    
    @Log @Message("Recipe '%s' created.")
    void created(String recipe);
    
    @Log @Message("Recipe '%s' edited.")
    void edited(String recipe);
    
    @Log @Message("Recipe '%s' found.")
    void found(String recipe);
    
    @Log @Message("Recipe '%s' deleted.")
    void deleted(String recipe);
}
