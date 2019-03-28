package Default.UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.File;

public class DeletePJUI extends BaseUI {
    private ComboBox comboBox = new ComboBox();
    private File folder = new File(System.getProperty("user.home") + "/ConstructionProject/ProjectFiles");
    private Boolean verbose = false;
    private File[] listOfFiles = folder.listFiles();
    private Pane pane = new Pane();
    private Button continueButton = new Button("Continue");
    private Button backButton = new Button("Back");
    private VBox vBox = new VBox(10);

    public DeletePJUI() {
        comboBox.setPromptText("Select A Project To Delete");
        refreshListOfFiles();

        continueButton.setDisable(true);
        comboBox.getSelectionModel().clearSelection();
        pane.getChildren().add(comboBox);
        pane.setPadding(insetsBase);
        pane.setTranslateX(10);
        pane.setTranslateY(10);
        borderPaneBase.setLeft(pane);
        vBox.setTranslateX(700);
        vBox.setTranslateY(250);
        vBox.getChildren().addAll(continueButton, backButton);
        backButton.setMinWidth(75);
        borderPaneBase.setBottom(vBox);

        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                continueButton.setDisable(false);
            }
        });
    }


    public void refreshListOfFiles() {
        continueButton.setDisable(true);
        comboBox.getItems().clear();

        if(folder.exists())
            for(File file : folder.listFiles())
            {
                if (verbose)
                    System.out.print("");
                if(!file.getName().contains(".DS_Store"))
                    comboBox.getItems().add(file.getName());

            }

    }

    public BorderPane getBorderPane() {
        return borderPaneBase;
    }

    public Button getContinueButton() {
        return continueButton;
    }

    public ComboBox getComboBox() {
        return comboBox;
    }

    public Button getBackButton() {
        return backButton;
    }
}