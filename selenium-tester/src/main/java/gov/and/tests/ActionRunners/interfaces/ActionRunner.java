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
 * Un ejecutor de cualquier acción no incluida en un script.
 * @author nesto
 */
@FunctionalInterface
public interface ActionRunner {
   void  run(WebDriver driver);
}
