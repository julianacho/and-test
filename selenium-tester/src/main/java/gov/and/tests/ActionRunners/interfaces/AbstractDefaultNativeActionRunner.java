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
package gov.and.tests.ActionRunners.interfaces;

import org.openqa.selenium.WebDriver;

/**
 * Definici�n de acci�n para implementaci�n nativa.
 * @author nesto
 */
public abstract class AbstractDefaultNativeActionRunner implements ActionRunner{
    /**
     * Ponga aqu� el nombre de la dll a cargar.
     * 
     * @return 
     */
    public abstract String getLibPath();
    
    @Override
    public void run(WebDriver driver) {
        System.loadLibrary(getLibPath());
    }
    
}
