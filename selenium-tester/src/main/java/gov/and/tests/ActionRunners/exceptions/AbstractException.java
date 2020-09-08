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
package gov.and.tests.ActionRunners.exceptions;

import lombok.Data;

/**
 *
 * @author Administrator
 */
@Data
public abstract class AbstractException extends Exception {
    private String context;
    /**
     * Creates a new instance of <code>AbstractException</code> without detail
     * message.
     */
    public AbstractException() {
    }

    /**
     * Constructs an instance of <code>AbstractException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AbstractException(String msg) {
        super(msg);
    }
}
