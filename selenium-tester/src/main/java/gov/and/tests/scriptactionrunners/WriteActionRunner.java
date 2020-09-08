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

import gov.and.tests.Utils;
import gov.and.tests.actions.TestAction;
import gov.and.tests.actionrunners.exceptions.InvalidActionException;
import gov.and.tests.actionrunners.exceptions.NoActionSupportedException;
import gov.and.tests.actionrunners.interfaces.AbstractCssSelectorActionRunner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Usa la funcion {@link WebElement#sendKeys(java.lang.CharSequence...) }, buscando el elemento en
 * el navegador por {@link By#cssSelector(java.lang.String) Selector css}
 * @author nesto
 */
public class WriteActionRunner extends AbstractCssSelectorActionRunner{
    private String text;
    public WriteActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        extractText(action.getCommand());
    }

    @Override
    public String getActionName() {
        return "escribir";
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        get(driver).sendKeys(text);
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        log.log(Level.INFO, "escribiendo \"{0}\" en {1}", new Object[]{text, getSelector()});
        run(driver);
    }

    private void extractText(String text) throws InvalidActionException {
        try {
            this.text = Utils.getJSONAttribute(text, "texto");
        } catch (ParseException ex) {
            throw new InvalidActionException(text);
        }
    }
}
