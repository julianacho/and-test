/*
 * Proyecto FURAG
 * 
 * Software para el Departamento de la Funci?n P?blica 
 * 
 * Permite medir la gesti?n de las entidades institucionales a trav?s de unos formularios (conjuntos de preguntas) que se pueden personalizar dependiendo de la pol?tica que se est? aplicando.
 * 
 * 
 * 
 *  Agencia Nacional Digital  de Gobierno  - https://and.gov.co/
 * 
 * Todos los derechos reservados 2020.
 */
package gov.and.tests.globals;

import gov.and.tests.Utils;
import gov.and.tests.actionrunners.exceptions.BadSyntaxException;
import gov.and.tests.actionrunners.exceptions.NoActionSupportedException;
import gov.and.tests.actionrunners.interfaces.AbstractDefaultScriptActionRunner;
import gov.and.tests.actions.TestAction;
import gov.and.tests.webapptester.MainApp;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import gov.and.tests.actionrunners.interfaces.ScriptActionRunner;
import java.lang.reflect.InvocationTargetException;

/**
 * Gestor de acciones.
 *
 * @author nesto
 */
@Data
public final class ActionRunnerManager {

    private static ResourceBundle globals = ResourceBundle.getBundle("application");

    public static void quit() {
        if (instance.driver != null) {
            instance.driver.quit();
        }
    }

    public static AbstractDefaultScriptActionRunner findRunner(String actionCommand) throws BadSyntaxException {
                TestAction action;
                    action = new TestAction(actionCommand);
                AbstractDefaultScriptActionRunner runner = null;
                for (Class runnerCls : ActionRunnerManager.runnersCls) {
                    try {
                        final Constructor constructor = runnerCls.getConstructor(TestAction.class);
                        runner = (AbstractDefaultScriptActionRunner) constructor.newInstance(action);
                        break;
                    } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                        //Runner incorrecto, va con otro.
                    }
                }
                return runner;
    }

    public enum BROWSERTYPE {
        CHROME,
        EDGE,
        FIREFOX,
        INTERNET_EXPLORER,
        OPERA,
        SAFARI
    }

    public enum ACTIONTYPE {
        INICIO,
        FIN
    }
    /**
     * Listado con todas las clases de funciones registradas.
     */
    private static List<Class<? extends ScriptActionRunner>> runnersCls = null;

    static {
        try {
            runnersCls = findRunnersCls();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ActionRunnerManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ActionRunnerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Listado con todas las funciones registradas.
     */
    private TreeModel rootTree;
    private BROWSERTYPE browserType;
    private WebDriver driver;

    private static ActionRunnerManager instance = new ActionRunnerManager();

    private ActionRunnerManager() {
        //Clases
        try {
            //Carpetas
            rootTree = asTree();
            //Acciones
        } //TODO: Mandar estas excepciones a UI
        catch (Exception ex) {
            Logger.getLogger("Probador Web").log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (driver != null) {
            driver.quit();
        }
    }

    public static TreeModel getTreeModel() {
        try {
            instance.rootTree = asTree();
        } catch (IOException ex) {
            Logger.getLogger("Probador Web").log(Level.SEVERE, null, ex);
        }
        return instance.getRootTree();
    }

    /**
     * Establece el tipo de navegador a utilizar PRE: btype != null
     *
     * @param btype
     */
    public static void set(BROWSERTYPE btype) {
        final BROWSERTYPE instanceBrowserType = instance.getBrowserType();
        if (instanceBrowserType != null) {
            if (instanceBrowserType == btype) {
                return;
            }
        }
        instance.setBrowserType(btype);
        final WebDriver instance_driver = instance.getDriver();
        if (instance_driver != null) {
            instance_driver.quit();
        }
        WebDriver driver = null;
        try {
            switch (btype) {
                case CHROME:
                    driver = new ChromeDriver();
                    break;
                case EDGE:
                    driver = new EdgeDriver();
                    break;
                case FIREFOX:
                    driver = new FirefoxDriver();
                    break;
                case INTERNET_EXPLORER:
                    driver = new InternetExplorerDriver();
                    break;
                case OPERA:
                    driver = new OperaDriver();
                    break;
                case SAFARI:
                default:
                    driver = new SafariDriver();
                    break;
            }
        } catch (Exception e) {
            String message = globals.getString("settings.driver.createException")
                    .replace("{0}", btype.name());
            JOptionPane.showMessageDialog(null, message,
                    globals.getString("globals.error.title"),
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        instance.setDriver(driver);
    }

    /**
     * Ejecuta una opcion del arbol
     *
     * @param item
     */
    public static void exec(TreePath item, Logger log) throws IOException {
        File file = Utils.getFile(item);
        final String ERR_TITLE = globals.getString("globals.error.title");
        if (file.isDirectory()) {
            JOptionPane.showMessageDialog(null, globals.getString("exec.err.isFolder"), ERR_TITLE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<Exception> exceptions = new LinkedList<>();
        for (File f : Utils.getRunnableFiles(item)) {
            exceptions.addAll(exec(f, log));
        }
        //Errores de la ejecucion
        if (!exceptions.isEmpty()) {
            MainApp.addException(exceptions.toArray(new Exception[]{}));
        }
    }

    /**
     * Busca en un directorio y retorna un arbol dependiendo de lo que encuentre
     * alli.
     *
     * @param folderName
     * @return
     */
    private static TreeModel asTree() throws IOException {
        ResourceBundle globals = ResourceBundle.getBundle("application");
        final String FOLDER_NAME = globals.getString("globals.actionfolder.name");
        File rootFile = new File(FOLDER_NAME);
        if (!rootFile.exists()) {
            final String message = globals.getString("globals.folder.read.errNoFolder");
            final String title = globals.getString("globals.error.title");
            JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
            Utils.buildDefaultActionRunnerFolder(FOLDER_NAME);
            rootFile = new File(FOLDER_NAME);
        }
        final TreeModel model = new DefaultTreeModel(fileAsTree(rootFile));
        return model;
    }

    private static MutableTreeNode fileAsTree(File file) {
        final DefaultMutableTreeNode resp = new DefaultMutableTreeNode(file.getName());
        final File[] children = file.listFiles();
        final String[] keyNamesArr = new String[]{"fin.txt", "inicio.txt"};
        try {
            for (MutableTreeNode node : Arrays.asList(children)
                    .stream()
                    //Se aceptan directorios o archivos diferentes a inicio,fin.txt
                    .filter(f -> f.isDirectory()
                    || Arrays.binarySearch(keyNamesArr, f.getName()) < 0)
                    .map(f -> fileAsTree(f))
                    .collect(toList())) {
                resp.add(node);
            }
        } catch (NullPointerException npe) {
            ;
        }
        return resp;
    }

    private static List<Class<? extends ScriptActionRunner>> findRunnersCls() throws ClassNotFoundException, IOException {
        final Class<? extends ScriptActionRunner>[] classes = Utils.getClasses("gov.and.tests.scriptactionrunners", ScriptActionRunner.class);
        List<Class<? extends ScriptActionRunner>> resp
                = Arrays.asList(classes);
        return resp;
    }

    /**
     * Ejecuta las instrucciones en un archivo.
     *
     * @param file
     * @param log
     */
    private static List<Exception> exec(File file, Logger log) throws IOException {
        //Uno quiere hacer las vainas bien, pero el universo conspira...
//        final List<String> filteredLines = Files.lines(FileSystems.getDefault().getPath(file.getAbsolutePath()))
//                .map(String::trim)
//                .filter(l -> !l.isEmpty() && !l.startsWith("#"))
//                .collect(toList());
        List<Exception> resp = new LinkedList<>();
        final List<String> filteredLines = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }
            filteredLines.add(line);
        };
        reader.close();

        List<String> command = new LinkedList<>();
        int lineCounter = 0;
        while (lineCounter < filteredLines.size()) {
            boolean correct = true;
            command.add(filteredLines.get(lineCounter++));
            final String actionCommand = command.stream().collect(joining(" "));
            correct = !command.isEmpty();
            if (correct) {
                AbstractDefaultScriptActionRunner runner;
                try {
                    runner = findRunner(actionCommand);
                } catch (BadSyntaxException ex) {
                    continue;
                }
                if (runner == null) {
                    String message = globals.getString("exec.err.noSuchRunnerException")
                            .replace("{0}", actionCommand)
                            .replace("{1}", file.getAbsolutePath());
                    resp.add(new NoActionSupportedException(message));
//                    JOptionPane.showMessageDialog(null, message,
//                            globals.getString("globals.error.title"), JOptionPane.ERROR_MESSAGE);
                    log.severe(message);
                    command.clear();
                    continue;
                }

                try {
                    runner.run(instance.getDriver(), log);
                } catch (Exception ex) {
//                    throwBadSynstaxEx(actionCommand, file, log);
                    resp.add(new BadSyntaxException(prepareBadSystaxExMsg(actionCommand, file)));
                } finally {
                    command.clear();
                }
            }
        }
        if (!command.isEmpty()) {
            throwBadSynstaxEx(command.stream().collect(joining(" ")), file, log);
        }
        return resp;
    }

    private static void throwBadSynstaxEx(final String actionCommand, File file, Logger log) throws HeadlessException {
        String badSyntazMsg = prepareBadSystaxExMsg(actionCommand, file);
        JOptionPane.showMessageDialog(null, badSyntazMsg,
                globals.getString("globals.error.title"), JOptionPane.ERROR_MESSAGE);
        log.severe(badSyntazMsg);
    }

    public static String prepareBadSystaxExMsg(final String actionCommand, File file) {
        String badSyntazMsg = globals.getString("exec.err.syntaxException")
                .replace("{0}", actionCommand)
                .replace("{1}", file.getAbsolutePath());
        return badSyntazMsg;
    }

}
