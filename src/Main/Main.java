package Main;

import java.io.IOException;

import Model.Model;
import myGUI.GUIDelegate;

/**
 * The Main class serves as the entry point for the GUI application.
 * It initializes the Model and GUIDelegate components to set up the
 * application.
 * 
 */
public class Main {
    /**
     * Constructor for the Main class.
     * 
     * @param model The Model instance used to manage the application data.
     */
    public Main(Model model) {
    }

    /**
     * The main method to run the model-delegate GUI application.
     * This method creates an instance of Model and GUIDelegate to start the
     * application.
     *
     * @param args Command line arguments (not used in this application).
     * @throws IOException If an I/O error occurs during the application setup.
     */
    public static void main(String[] args) throws IOException {
        Model model = new Model();
        GUIDelegate guiDelegate = new GUIDelegate(model);
    }
}