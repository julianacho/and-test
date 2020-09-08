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
package gov.and.tests.actions;

import gov.and.tests.actionrunners.exceptions.BadSyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import gov.and.tests.actionrunners.interfaces.ScriptActionRunner;

/**
 * Una accion debe tener:
 * un nombre unico, que corresponda con un {@link ActionRunner}
 * un comando que sera interpretado por ese ActionRunner
 * Ejemplo:
 * go:{http://furagdev.and.gov.co}
 * @author nesto
 */
@Getter
public class TestAction {
    private String name;
    private String command;
    /**
     * Constructora a partir de un comando completo.
     * @param fullCommand 
     * @throws gov.and.tests.actionrunners.exceptions.BadSyntaxException 
     */
    public TestAction(String fullCommand) throws BadSyntaxException{
        final Pattern pattern = Pattern.compile("^(.*)=\\{(.*)\\}$",Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(fullCommand);
        if(! matcher.matches() || matcher.groupCount()<2){
            throw new BadSyntaxException(fullCommand);
        }
        int counter = 1;
        name=matcher.group(counter++).toLowerCase();
        command="{"+matcher.group(counter++)+"}";
    }
}
