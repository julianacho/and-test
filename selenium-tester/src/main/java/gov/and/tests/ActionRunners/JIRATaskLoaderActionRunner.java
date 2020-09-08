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
package gov.and.tests.ActionRunners;

import gov.and.tests.ActionRunners.interfaces.AbstractDefaultNativeActionRunner;
import gov.and.tests.ActionRunners.interfaces.ActionRunner;
import org.openqa.selenium.WebDriver;

/**
 * Carga mi reporte de horas a JIRA.
 * @author nesto
 */
public class JIRATaskLoaderActionRunner extends AbstractDefaultNativeActionRunner{

    @Override
    public String getLibPath() {
        return "jirataskloader.dll";
    }

    /**
     * Para implementar esto en cualquier lenguaje...
     * @param driver 
     */
     private native void nativeRun(WebDriver driver);

    @Override
    public void run(WebDriver driver) {
        super.run(driver); 
        nativeRun(driver);
    }

}
