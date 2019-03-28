package Default.UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sun.misc.Launcher;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Description:
 * Programmer : Braulio Duarte
 * Project/Lab: Programming Exercise
 * Last Modified: 2/25/17.
 */
public class OpenPJUI extends BaseUI {
    private ComboBox comboBox = new ComboBox();
    private File folder = new File(System.getProperty("user.home") + "/ConstructionProject/ProjectFiles");
    private File[] listOfFiles = folder.listFiles();
    private Pane pane = new Pane();
    private Button continueButton = new Button("Continue");
    private Button backButton = new Button("Back");
    private VBox vBox = new VBox(10);
    private boolean verbose = false;

    public OpenPJUI()
    {

        continueButton.setDisable(true);
        refreshListOfFiles();

        comboBox.setPromptText("Select A Project");


        comboBox.getSelectionModel().clearSelection();
        pane.getChildren().add(comboBox);
        pane.setPadding(insetsBase);
        pane.setTranslateX(10);
        pane.setTranslateY(10);
        borderPaneBase.setLeft(pane);
        vBox.setTranslateX(700);
        vBox.setTranslateY(250);
        vBox.getChildren().addAll(continueButton,backButton);
        backButton.setMinWidth(75);
        borderPaneBase.setBottom(vBox);

        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                continueButton.setDisable(false);
            }
        });
    }



    public void refreshListOfFiles()
    {
        continueButton.setDisable(true);
        comboBox.getItems().clear();

        final String path = System.getProperty("user.home") + "/ConstructionProject/ProjectFiles";
        final File folder = new File(path);

        try
        {
            for(File file : folder.listFiles())
            {
                if(!file.getName().contains(".DS_Store"))
                    comboBox.getItems().add(file.getName());
                if (verbose)
                    System.out.println(file.getName());
            }
        }catch (NullPointerException e)
        {
            System.out.println(e);
        }


    }

    public BorderPane getBorderPane() { return borderPaneBase; }
    public Button getContinueButton() { return continueButton; }
    public ComboBox getComboBox() { return comboBox; }
    public Button getBackButton() {
        return backButton;
    }
}


       /* for (File listOfFile : listOfFiles) {
            if(!listOfFile.getName().contains(".DS_Store"))
                comboBox.getItems().add(listOfFile.getName());
        }*/