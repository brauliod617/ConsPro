package Default.UI;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 * Description:
 * Programmer : Braulio Duarte
 * Project/Lab: Programming Exercise
 * Last Modified: 2/24/17.
 */
public class HomeScreenUI extends BaseUI {
    private Button newProjectButton = new Button("Create New Project");
    private Button openProjectButton = new Button("Open New Project");
    private Button deleteProjectButton = new Button("Delete Project");

    public HomeScreenUI()
    {
        newProjectButton.setTranslateX(-40);
        openProjectButton.setTranslateX(-40);
        deleteProjectButton.setTranslateX(-40);
        hBoxTopBase.getChildren().addAll(newProjectButton, openProjectButton, deleteProjectButton);
        borderPaneBase.setLeft(logoIv);
    }

    public BorderPane getHSBorderPane()
    {
        return borderPaneBase;
    }
    public void deleteHomeScreenUI()
    {
        hBoxTopBase.getChildren().removeAll(newProjectButton, openProjectButton, deleteProjectButton);
        borderPaneBase.setLeft(null);
    }
    public void resetHomeScreenUI()
    {
        if(!hBoxTopBase.getChildren().contains(newProjectButton))
            hBoxTopBase.getChildren().add(newProjectButton);
        if(!hBoxTopBase.getChildren().contains(openProjectButton))
            hBoxTopBase.getChildren().add(openProjectButton);
        if(!hBoxTopBase.getChildren().contains(deleteProjectButton))
            hBoxTopBase.getChildren().add(deleteProjectButton);

        deleteProjectButton.setTranslateX(-40);
        newProjectButton.setTranslateX(-40);
        openProjectButton.setTranslateX(-40);
        if(!borderPaneBase.getChildren().contains(logoIv))
            borderPaneBase.setLeft(logoIv);
    }
    public Button getNewPJButton()
    {
        return newProjectButton;
    }
    public Button getOpenPJButton()
    {
        return openProjectButton;
    }
    public Button getDeleteProjectButton() {
        return deleteProjectButton;
    }
}
