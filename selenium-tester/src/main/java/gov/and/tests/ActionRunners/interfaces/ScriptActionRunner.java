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
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Definicion basica de un lector de instrucciones para el tester.
 * Estas instrucciones se ejecutaran con driver y Selenium.
 * @author nesto
 */
public interface ScriptActionRunner {
    /**
     * La accion debe ser distintiva, unica entre todos los parsers
     * Nota: Se maneja en minusculas.
     * @return 
     */
    public String getActionName();
    /**
     * Ejecucion del script correspondiente.
     * @param driver
     * @throws Exception 
     */
    public void run(WebDriver driver) throws Exception;
    /**
     * Ejecucion del script correspondiente.
     * @param driver
     * @param log Archivo de registro
     * @throws Exception 
     */
    public void run(WebDriver driver,Logger log) throws Exception;
    /**
     * Si este parser soporta este comando o no.
     * @param action
     * @return 
     */
    public default boolean matches(TestAction action){
        return action.getName().equals(getActionName());
    }
}
