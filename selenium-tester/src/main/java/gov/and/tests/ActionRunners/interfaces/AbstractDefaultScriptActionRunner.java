/*
 * Proyecto FURAG
 * 
 * Software para el Departamento de la Funci�n P�blica 
 * 
 * Permite medir la gesti�n de las entidades institucionales a trav�s de unos formularios (conjuntos de preguntas) que se pueden personalizar dependiendo de la pol�tica que se est� aplicando.
 * 
 * 
 * 
 *  Agencia Nacional Digital  de Gobierno  - https://and.gov.co/
 * 
 * Todos los derechos reservados 2020.
 */
package gov.and.tests.actionrunners.interfaces;

import gov.and.tests.actions.TestAction;
import gov.and.tests.actionrunners.exceptions.InvalidActionException;
import gov.and.tests.actionrunners.exceptions.NoActionSupportedException;
import java.util.logging.Logger;
import lombok.Getter;
import org.openqa.selenium.WebDriver;

/**
 * @author nesto
 */
@Getter
public abstract class AbstractDefaultScriptActionRunner implements ScriptActionRunner {

    private TestAction action;
    
    public AbstractDefaultScriptActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        if (action == null) {
            throw new IllegalArgumentException(action.getName());
        }
        if (!matches(action)) {
            final NoActionSupportedException except = new NoActionSupportedException(action.getName());
            except.setRunnerAction(getActionName());
            throw except;
        }
        this.action = action;
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        log.info("Ejecutando "+getActionName()+" en "+action.getCommand());
        run(driver);
    }

}
