/*
 * Proyecto FURAG
 * 
 * Software para el Departamento de la Función Pública 
 * 
 * Permite medir la gestión de las entidades institucionales a través de unos formularios (conjuntos de preguntas) que se pueden personalizar dependiendo de la política que se esté aplicando.
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
 * Definición de acción para implementación nativa.
 * @author nesto
 */
public abstract class AbstractDefaultNativeActionRunner implements ActionRunner{
    /**
     * Ponga aquí el nombre de la dll a cargar.
     * 
     * @return 
     */
    public abstract String getLibPath();
    
    @Override
    public void run(WebDriver driver) {
        System.loadLibrary(getLibPath());
    }
    
}
