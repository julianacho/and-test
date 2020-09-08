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

import gov.and.tests.Utils;
import gov.and.tests.actionrunners.exceptions.BadSyntaxException;
import gov.and.tests.actions.TestAction;
import gov.and.tests.actionrunners.exceptions.InvalidActionException;
import gov.and.tests.actionrunners.exceptions.NoActionSupportedException;
import lombok.Getter;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Parser basado en seleccion de elementos por seleccion css
 * @author nesto
 */
@Getter
public abstract class AbstractCssSelectorActionRunner extends AbstractDefaultScriptActionRunner{
    /**
     * El selector usado.
     */
    private String selector;
    
    public AbstractCssSelectorActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
    }
    
    protected WebElement get(WebDriver driver) throws BadSyntaxException{
        final String actionCommand = getAction().getCommand();
        try {
            selector =Utils.getJSONAttribute(actionCommand, "selector");
        } catch (ParseException ex) {
            final BadSyntaxException badSyntaxException = new BadSyntaxException(actionCommand);
            throw badSyntaxException;
        }
        return driver.findElement(By.cssSelector(selector));
    }
    
}
