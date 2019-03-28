package Default.UI;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Vector;

/**
 * Description: Input Employee Names
 * Programmer : Braulio Duarte
 * Project/Lab: Programming Exercise
 * Last Modified: 2/24/17.
 */
public class    CreateNewPJ2UI extends BaseUI {
    protected VBox employeeVbox = new VBox();
    protected BorderPane createNewPJ2BorderP = new BorderPane();
    protected ScrollBar scrollBar = new ScrollBar();
    protected Vector<TextField> textFieldVector = new Vector<>();
    protected Vector<Text> textVector = new Vector<>();
    protected Button continueButton = new Button("Continue");
    protected Button backButton = new Button("Back");
    private int sbValueModifier;

    public CreateNewPJ2UI(int numOfEmployyes)
    {
        employeeVbox.setSpacing(10);
        employeeVbox.setPadding(insetsBase);

        for (int i = 0; i < numOfEmployyes; i++) {
            textVector.add(new Text("Enter Employee Name " + (i + 1)));
            textFieldVector.add(new TextField());
            textFieldVector.get(i).setMaxWidth(180);
            textFieldVector.get(i).setMinWidth(180);

            employeeVbox.getChildren().add(textVector.get(i));
            employeeVbox.getChildren().add(textFieldVector.get(i));
        }
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setMin(0);
        scrollBar.setMax(numOfEmployyes);
        scrollBar.setValue(0);
        scrollBar.setUnitIncrement(1);
        scrollBar.setBlockIncrement(2);
        scrollBar.setMaxHeight(430);
        scrollBar.setMinHeight(430);
        scrollBar.setTranslateX(583);

        scrollBar.valueProperty().addListener((ov, old_val, new_val) -> employeeVbox.setTranslateY(-(new_val.doubleValue() * (numOfEmployyes + caculateModifier(numOfEmployyes)))));
        employeeVbox.getChildren().addAll(continueButton, backButton);
        createNewPJ2BorderP.setRight(scrollBar);
        createNewPJ2BorderP.setLeft(employeeVbox);

    }


    private int caculateModifier(int input)
    {
        if(input < 140)
        return 60 - input;
        else if(input >= 140 && input < 190)
        return 62 - input;
        else if (input >= 190)
        return 64 - input;

        return 60 - input;

    }
    public BorderPane getCreateNewPJ2BorderP()
    {
        return createNewPJ2BorderP;
    }
    public Button getContinueButton() { return continueButton; }

    public Button getBackButton() {
        return backButton;
    }

    public void deleteCreateNewPJ2() {

    }
    public Vector<TextField> getTextFieldVector() { return textFieldVector;  }
}
