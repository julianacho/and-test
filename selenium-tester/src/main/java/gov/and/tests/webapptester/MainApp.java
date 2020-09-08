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
package gov.and.tests.webapptester;

import gov.and.tests.ActionRunners.exceptions.AbstractException;
import gov.and.tests.Utils;
import gov.and.tests.globals.ActionRunnerManager;
import gov.and.tests.parser.SIDEToTesterParser;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.prefs.Preferences;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import lombok.Data;

//import static javafx.application.Application.launch;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
@Data
public class MainApp extends JFrame {

    private static MainApp instance;
    JTree rootTree = new JTree();
    JComboBox<String> browserTree = new JComboBox<String>();
    private JLabel label = new JLabel();
    private ResourceBundle globals = ResourceBundle.getBundle("application");
    private Image errImage, warnImage;
    private Canvas imageContainer;
    private final String NEW_LINE = System.getProperty("line.separator");

    public enum PROPS {
        JTREE,
        JCOMBOBOX
    }

    private enum APP_ICON_STATE {
        PLAIN,
        WARN,
        ERROR
    }

    private APP_ICON_STATE appState = APP_ICON_STATE.PLAIN;
    private List<AbstractException> exceptions = new LinkedList<>();

    private MainApp() {
//        setResizable(false);
        setIconImage(findImage("/icons/selenium.png"));
        setTitle("Probador formularios Web");
        init();
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                ;
            }

            @Override
            public void windowClosing(WindowEvent e) {
                ActionRunnerManager.quit();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
                ;
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
                pack();
            }

            @Override
            public void windowActivated(WindowEvent e) {
//                pack();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                ;
            }
        });
    }

    public static Object get(PROPS prop) {
        switch (prop) {
            case JCOMBOBOX:
                return instance.browserTree;
            case JTREE:
            default:
                return instance.rootTree;
        }
    }

    /**
     * Agrega errores a la lista recopilada.
     *
     * @param exceptions
     */
    public static void addException(Exception... exceptions) {
//        instance.exceptions.addAll(Arrays.asList(exceptions)
//                .stream().
//                map(ex -> ex instanceof AbstractException ? (AbstractException) ex
//                : new DefaultException(ex.getMessage())).
//                collect(toList()));
//        instance.appState = APP_ICON_STATE.WARN;
//        instance.label.setText(instance.globals.getString("exec.err.syntaxException.tooltip"));
//        instance.imageContainer.repaint();
//        instance.repaint();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        instance = new MainApp();
        instance.pack();
        instance.setVisible(true);
    }

    public final void init() {
        //Inicializacion de componentes
        final GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        final Dimension treeDimension = new Dimension(370, 370);
        rootTree.setSize(treeDimension);
        rootTree.setPreferredSize(treeDimension);
        rootTree.setMinimumSize(rootTree.getSize());
        final JScrollPane treeScrollPane = new JScrollPane(rootTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        final Dimension treeSize = new Dimension(330, 400);
//        treeScrollPane.setPreferredSize(treeSize);
//        treeScrollPane.setSize(treeScrollPane.getPreferredSize());
//        treeScrollPane.setMinimumSize(treeScrollPane.getSize());

        JButton goButton = new JButton("Vamos"), reloadButton = new JButton("Recargar"), parseSIButton = new JButton("Cargar script de Selenium IDE");
        parseSIButton.addActionListener(evt -> {
            JFileChooser chooser = new JFileChooser();
            chooser.addChoosableFileFilter(new FileNameExtensionFilter(
                    globals.getString("parser.import.filefilter.description"),
                    "side"));
            chooser.setDialogTitle(globals.getString("parser.import.title"));
            chooser.setAcceptAllFileFilterUsed(true);
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            chooser.showOpenDialog(this);
            final File SIDEfile = chooser.getSelectedFile();
            if (SIDEfile == null) {
                return;
            }
            File directory = new File("Acciones");
            SIDEToTesterParser parser = new SIDEToTesterParser();
            try {
                directory = new File(directory, globals.getString("parser.import.directory"));
                if (!directory.exists()) {
                    directory.mkdir();
                }
                parser.parse(SIDEfile);
                parser.saveAll(directory);
                rootTree.setModel(ActionRunnerManager.getTreeModel());
                repaint();
                JOptionPane.showMessageDialog(this, globals.getString("parser.side.done"));
            } catch (Exception e) {
                addException(e);
            }
        });
        goButton.setSize(new Dimension(90, 26));
        goButton.setPreferredSize(goButton.getSize());
        goButton.addActionListener(this::goAction);
        reloadButton.setSize(goButton.getSize());
        reloadButton.setPreferredSize(reloadButton.getSize());
        reloadButton.addActionListener(evt -> {
            rootTree.setModel(ActionRunnerManager.getTreeModel());
            repaint();
        });

        label.setPreferredSize(new Dimension(330, 26));
        label.setSize(label.getPreferredSize());
        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (exceptions.isEmpty()) {
                    return;
                }
                final String ERR_TITLE = globals.getString("globals.error.title");
                final String lineSep = System.getProperty("line.separator");

                String msg = exceptions.stream()
                        .map(ae -> {
                            final String context = ae.getContext();
                            return (context == null ? "" : context + NEW_LINE)
                                    + Utils.getFirstLine(ae);
                        })
                        .collect(joining(lineSep));
                if (msg.trim().isEmpty()) {
                    return;
                }
                final JTextArea ta = new JTextArea(msg);
                ta.setEditable(false);
                final Dimension preferredSize = new Dimension(500, 400);
                ta.setSize(preferredSize);
                ta.setPreferredSize(preferredSize);
                final JScrollPane taPane = new JScrollPane(ta,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//                final int BORDER = 20;
//                taPane.setPreferredSize(new Dimension(preferredSize.width + BORDER, preferredSize.height + BORDER));
//                taPane.add(ta);
                JOptionPane.showMessageDialog(null, taPane, ERR_TITLE, JOptionPane.ERROR_MESSAGE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        imageContainer = new Canvas() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                switch (appState) {
                    case ERROR:
                        g.drawImage(errImage, 0, 0, null);
                        break;
                    case WARN:
                        g.drawImage(warnImage, 0, 0, null);
                        break;
                }
            }
        };
        imageContainer.setPreferredSize(new Dimension(20, 20));
        imageContainer.setMinimumSize(imageContainer.getPreferredSize());
        errImage = findImage("/icons/error.png");
        warnImage = findImage("/icons/warning.png");
        for (String item : Arrays.asList(ActionRunnerManager.BROWSERTYPE.values())
                .stream()
                .map(bn -> globals.getString("settings.driver." + bn.name() + ".name"))
                .collect(toList())) {
            browserTree.addItem(item);
        }
        browserTree.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateBrowser(true);
            }
        });
        final String storedBrowserName = Preferences.userRoot().get("webapptester.browser", null);
        if (storedBrowserName != null) {
            final ActionRunnerManager.BROWSERTYPE storedBrowser
                    = ActionRunnerManager.BROWSERTYPE.valueOf(storedBrowserName);
            ActionRunnerManager.set(storedBrowser);
            browserTree.setSelectedItem(globals.getString("settings.driver." + storedBrowserName + ".name"));
        }
        updateBrowser(false);
        rootTree.setModel(ActionRunnerManager.getTreeModel());
//Distrubucion grafica
        setLayout(layout);
        gbc.gridwidth = 3;
        gbc.gridx = gbc.gridy = 0;
        getContentPane().add(treeScrollPane, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;

        getContentPane().add(goButton, gbc);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.CENTER;
        getContentPane().add(reloadButton, gbc);
        gbc.gridx++;
        getContentPane().add(browserTree, gbc);
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy++;
        getContentPane().add(parseSIButton, gbc);
        gbc.gridwidth = 2;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        getContentPane().add(label, gbc);
        gbc.gridwidth = 1;
        gbc.gridx += 2;
        getContentPane().add(imageContainer, gbc);
    }

    /**
     * Metodo para el boton "Vamos
     *
     * @param e
     */
    public void goAction(ActionEvent e) {
        final String ERR_TITLE = globals.getString("globals.error.title");
        //No selecciono navegador?
        if (browserTree.getSelectedIndex() == -1) {
            String message = globals.getString("settings.driver.emptyException");
            JOptionPane.showMessageDialog(null, message, ERR_TITLE, JOptionPane.WARNING_MESSAGE);
            return;
        }
        Logger log = Logger.getLogger("Probador Web");
        final FileHandler fileHandler;
        try {
            fileHandler = new FileHandler("ProbadorWeb.log");
            fileHandler.setFormatter(new SimpleFormatter());
            log.addHandler(fileHandler);
        } catch (IOException ex) {
            Logger.getLogger("Probador Web").log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger("Probador Web").log(Level.SEVERE, null, ex);
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ActionRunnerManager.exec(rootTree.getSelectionModel().getSelectionPath(), log);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString(), ERR_TITLE, JOptionPane.ERROR_MESSAGE);
                    log.log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }

    /**
     * Actualiza el navegador a partir del estado del combo de navegadores
     *
     * @param withUsrMsg
     * @return El tipo de navegador seleccionado, o null si ninguno lo fue.
     */
    private ActionRunnerManager.BROWSERTYPE updateBrowser(boolean withUsrMsg) throws HeadlessException {
        final String item = (String) browserTree.getSelectedItem();
        if (item == null) {
            return null;
        }
        final Optional<ActionRunnerManager.BROWSERTYPE> browserMatch = Arrays.asList(ActionRunnerManager.BROWSERTYPE.values())
                .parallelStream()
                .filter(bn -> item.equals(globals.getString("settings.driver." + bn.name() + ".name")))
                .findFirst();
        if (browserMatch.isPresent()) {
            final ActionRunnerManager.BROWSERTYPE resp = browserMatch.get();
            ActionRunnerManager.set(resp);
            Preferences.userRoot().put("webapptester.browser", resp.name());
            return resp;
        } else {
            if (withUsrMsg) {
                String msg = globals.getString("settings.driver.emptyException");
                JOptionPane.showMessageDialog(null, msg, globals.getString("globals.error.title"), JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    public final Image findImage(final String iconPath) {
        try {
            Image img = ImageIO.read(getClass().getResource(iconPath));
            return img;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
