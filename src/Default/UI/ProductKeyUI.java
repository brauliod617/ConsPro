package Default.UI;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Description:
 * Programmer : Braulio Duarte
 * Project/Lab: Programming Exercise
 * Last Modified: 2/25/17.
 */
public class ProductKeyUI extends BaseUI {
    private TextField textField = new TextField();
    private HBox hBox = new HBox();
    private VBox vBox = new VBox();
    private Label label = new Label();
    private Button continueButton = new Button("Continue");

    public ProductKeyUI()
    {
        label.setText("Enter Product Key");
        label.setLabelFor(textField);

        hBox.getChildren().addAll(label,textField);
        hBox.setTranslateX(100);
        hBox.setSpacing(10);
        hBox.setPadding(insetsBase);

        continueButton.setTranslateX(300);
        vBox.getChildren().addAll(hBox, continueButton);

        borderPaneBase.setCenter(vBox);
    }

    public BorderPane getBorderPane()
    {
        return borderPaneBase;
    }

    public TextField getTextField()
    {
        return textField;
    }

    public Button getContinueButton()
    {
        return continueButton;
    }

    public void deleteProductKeyUI()
    {
        borderPaneBase.getChildren().removeAll(vBox);
    }

    public int checkDir()
    {
        String path = System.getProperty("user.home");
        path += File.separator + "ConstructionProject";

        File customDir = new File(path);

        if(customDir.exists()){     //used to dertermine if program is already installed in pc
            return 1;
        } else if (customDir.mkdir()) {
            return 2;
        } else {
            return 3;
        }
    }

    public boolean checkKey(TextField textField)
    {
        boolean match = false;
        String keyString;
        ArrayList<String> strings = new ArrayList<>();
        File keyFile = new File(System.getProperty("user.home") + "/ConstructionProject/y.txt");
        if(!keyFile.exists())
        {
            System.out.println("File Does not exist");
            System.exit(0);
        }
        try {
            Scanner input = new Scanner(keyFile);

            while (input.hasNext())     //read from the key file
            {
                keyString = input.nextLine();
                System.out.println("keyString = " + keyString);
                if(keyString.equals(textField.getText()))   //if key file matchs user input
                {
                    match = true;
                }
                else
                {
                    strings.add(keyString);         //store all keys except users key into string array list
                }

            }
            if (match)          //rewrite key list file with out users key
            {
                PrintWriter printWriter = new PrintWriter(keyFile);

                for (String string : strings) {
                    System.out.println("String written into file = " + string);
                    printWriter.write(string);
                }
                printWriter.close();
            }
        }catch (Throwable throwable)
        {
        }
        return match;
    }
}
