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

/**
 * Para cuando alguien metio la pata escribiendo el comando.
 * @author nesto
 */
public class BadSyntaxException extends AbstractException {

    /**
     * Creates a new instance of <code>BadSyntaxException</code> without detail
     * message.
     */
    public BadSyntaxException() {
    }

    /**
     * Constructs an instance of <code>BadSyntaxException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BadSyntaxException(String msg) {
        super(msg);
    }
}
