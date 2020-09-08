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
package gov.and.tests.scriptactionrunners;

import gov.and.tests.actions.TestAction;
import gov.and.tests.actionrunners.exceptions.InvalidActionException;
import gov.and.tests.actionrunners.exceptions.NoActionSupportedException;
import gov.and.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author nesto
 */
public class GoActionRunner extends AbstractDefaultScriptActionRunner{

    private String url;
    public GoActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        final Pattern pattern = Pattern.compile("^\\{(.*)\\}$");
        final Matcher matcher = pattern.matcher(action.getCommand());
        if(!matcher.matches()){
            throw new InvalidActionException(action.getCommand());
        }
        url = matcher.group(1);
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        driver.get(url);
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        log.log(Level.INFO, "Abriendo direccion {0}", getAction().getCommand());
        run(driver);
    }

    @Override
    public String getActionName() {
        return "ir";
    }
    
}
