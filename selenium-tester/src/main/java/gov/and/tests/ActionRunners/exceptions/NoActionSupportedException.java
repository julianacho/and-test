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
package gov.and.tests.actionrunners.exceptions;

import gov.and.tests.ActionRunners.exceptions.AbstractException;
import lombok.Data;

/**
 * Para cuando un parser no es compatible con una accion
 * @author nesto
 */
@Data
public class NoActionSupportedException extends AbstractException {
    /**
     * La accion del ejecutor para la cual se intento ejecutar esta accion.
     */
    private String runnerAction;
    /**
     * Creates a new instance of <code>NoActionSupportedException</code> without
     * detail message.
     */
    public NoActionSupportedException() {
    }

    /**
     * Constructs an instance of <code>NoActionSupportedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public NoActionSupportedException(String msg) {
        super(msg);
    }
}
