package Default.UI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * Description:
 * Programmer : Braulio Duarte
 * Project/Lab: Programming Exercise
 * Last Modified: 2/24/17.
 */
public class CreateNewPJUI extends BaseUI{
    private Text[] texts = new Text[9];
    private TextField[] textFields = new TextField[8];
    private VBox vBox = new VBox();
    private HBox dateHbox = new HBox();
    private Button continueButton = new Button("Continue");
    private Button backButton = new Button("Back");
    private BorderPane newPjBorderPane = new BorderPane();
    private Insets newPJInsets = new Insets(10, 10, 10, 0);

    public CreateNewPJUI()
    {
        for (int i = 0; i < 9; i++)
        {
            texts[i] = new Text();
        }
        for (int i = 0; i < 8; i++)
        {
            textFields[i] = new TextField();
        }
        texts[0].setText("Enter Project Name");
        texts[1].setText("Enter Project Address");
        texts[2].setText("Enter Project Start Date");
        texts[3].setText("Month");
        texts[4].setText("Day");
        texts[5].setText("Year");
        texts[6].setText("Enter Project Price");
        texts[7].setText("How many employees will you use at the start of the project?");
        texts[8].setText("Click here when you are done");
        textFields[0].setMinWidth(460);
        textFields[0].setMaxWidth(460);
        textFields[0].setText("Project Name");
        textFields[1].setMinWidth(460);
        textFields[1].setMaxWidth(460);
        textFields[1].setText("Project Address");
        textFields[2].setMinWidth(460);
        textFields[2].setMaxWidth(460);
        textFields[2].setText("Project Start Date");
        textFields[3].setMinWidth(35);
        textFields[3].setMaxWidth(35);
        textFields[3].setText("00");
        textFields[4].setMaxWidth(35);
        textFields[4].setMinWidth(35);
        textFields[4].setText("00");
        textFields[5].setMinWidth(50);
        textFields[5].setMaxWidth(50);
        textFields[5].setText("0000");
        textFields[6].setMinWidth(460);
        textFields[6].setMaxWidth(460);
        textFields[6].setText("0.00");
        textFields[7].setMinWidth(50);
        textFields[7].setMaxWidth(50);
        textFields[7].setText("0");

        dateHbox.setSpacing(4);
        dateHbox.setPadding(newPJInsets);
        vBox.setSpacing(10);
        vBox.setPadding(insetsBase);

        dateHbox.getChildren().addAll( texts[3], textFields[3], texts[4], textFields[4], texts[5], textFields[5]);
        vBox.getChildren().addAll(texts[0], textFields[0], texts[1] ,textFields[1], dateHbox, texts[6] , textFields[6], texts[7] , textFields[7], texts[8],  continueButton, backButton);

    }
    public void resetTextfields()
    {
        textFields[0].setText("Project Name");
        textFields[1].setText("Project Address");
        textFields[2].setText("Project Start Date");
        textFields[3].setText("00");
        textFields[4].setText("00");
        textFields[5].setText("0000");
        textFields[6].setText("0.00");
        textFields[7].setText("0");
    }

    public void setUI()
    {
        newPjBorderPane.setTop(vBox);
        newPjBorderPane.setRight(logoIvMedium);
    }

    public void removeUI()
    {
        newPjBorderPane.getChildren().remove(vBox);
        newPjBorderPane.getChildren().remove(logoIvMedium);
    }

    public BorderPane getNewPjBorderPane()
    {
        return newPjBorderPane;
    }

    public Button getContinueButton() { return continueButton; }

    public Button getBackButton() {
        return backButton;
    }

    public TextField[] getTextFields() { return textFields; }
}
