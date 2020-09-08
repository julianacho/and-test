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
package oa.tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author nesto
 */
public class Miscelaneous {

    public Miscelaneous() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public static void main(String[] args) {
        try {
            final String separator = System.getProperty("file.separator");
            File file = new File("Acciones" + separator + "Europa" + separator + "Constantinopla.txt");
            final Stream<String> lines = Files.lines(FileSystems.getDefault().getPath(file.getAbsolutePath()));
            final Stream<String> ftrim = lines
                    .map(String::trim);
            final Stream<String> fempty = ftrim
                    .filter(l -> !l.isEmpty() && !l.startsWith("#"));
            List<String> filteredLines = fempty
                    .collect(toList());
        } catch (IOException ex) {
            Logger.getLogger("Probador Web").log(Level.SEVERE, null, ex);
        }
    }
}
