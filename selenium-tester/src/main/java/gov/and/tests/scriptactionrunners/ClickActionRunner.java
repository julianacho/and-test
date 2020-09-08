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
import gov.and.tests.actionrunners.interfaces.AbstractCssSelectorActionRunner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Usa la funcion {@link WebElement#click()}, buscando el elemento en el
 * navegador por {@link By#cssSelector(java.lang.String) Selector css}
 *
 * @author nesto
 */
public class ClickActionRunner extends AbstractCssSelectorActionRunner {

    public ClickActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        get(driver).click();
    }

    @Override
    public void run(WebDriver driver, Logger log) throws Exception {
        log.log(Level.INFO, "dando clic en el elemento {0}", getAction().getCommand());
        run(driver);
    }

    @Override
    public String getActionName() {
        return "clic";
    }

}
