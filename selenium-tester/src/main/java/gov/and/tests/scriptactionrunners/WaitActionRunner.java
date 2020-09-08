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
import gov.and.tests.actionrunners.exceptions.InvalidActionException;
import gov.and.tests.actionrunners.exceptions.NoActionSupportedException;
import gov.and.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import gov.and.tests.actions.TestAction;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
#esperar - Espera a que la página cargue completamente despues de un cambio de URL o despues de hacer clic en algun lado.
#esperará a que cierto objeto (dado su selector), aparezca o simplemente a que la página cargue completa
#Ejemplos:
esperar:{}

esperar:{selector:{#__next > div > div > main > div._3ZoET > div > div._2oVR5 > section > section:nth-child(2) > div}}
 * @author nesto
 */
public class WaitActionRunner extends AbstractDefaultScriptActionRunner{
    private String selector="body";
    
    public WaitActionRunner(TestAction action) throws NoActionSupportedException, InvalidActionException {
        super(action);
        final String actionCommand = action.getCommand();
        //Caso sencillo
        if(actionCommand.replace(" ", "").length()==2)
            return;
        try {
            selector = Utils.getJSONAttribute(actionCommand, "selector");
        } catch (ParseException ex) {
            throw new InvalidActionException(actionCommand);
        }
    }

    @Override
    public String getActionName() {
        return "esperar";
    }

    @Override
    public void run(WebDriver driver) throws Exception {
        new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
    }
    
//    @Override
//    public void run(WebDriver driver, Logger log) throws Exception {
//        log.log(Level.INFO, "esperando a que aparezca el elemento {0}", selector);
//        run(driver);
//    }
    
}
