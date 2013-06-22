package cz.muni.fi.pv243.cookbook.logging;

import org.jboss.solder.logging.Log;
import org.jboss.solder.logging.MessageLogger;
import org.jboss.solder.messages.Message;

/**
 *
 * @author fkiss
 */
@MessageLogger
public interface IngredientLogger {
    
    @Log @Message("Ingredient '%s' created.")
    void created(String ingredient);
    
    @Log @Message("Ingredient '%s' edited.")
    void edited(String ingredient);
    
    @Log @Message("Ingredient '%s' found.")
    void found(String ingredient);

    @Log @Message("Ingredient '%s' deleted.")
    void deleted(String ingredient);
}
