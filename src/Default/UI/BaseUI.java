package Default.UI;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Description:
 * Programmer : Braulio Duarte
 * Project/Lab: Programming Exercise
 * Last Modified: 2/24/17.
 */
public class BaseUI{

    private BackgroundFill bgFill = new BackgroundFill(Color.rgb(47, 60, 102), null, null);
    private Background bg = new Background(bgFill, null, null);
    protected BorderPane borderPaneBase = new BorderPane();   //border pane to be used for start screen
    protected Insets insetsBase = new Insets(10, 10, 10, 10);
    protected HBox hBoxTopBase = new HBox();
    protected Image logoImage = new Image("Images/logo.png");
    protected ImageView logoIvMini = new ImageView();
    protected ImageView logoIv = new ImageView();
    protected ImageView logoIvMedium = new ImageView();


    public BaseUI()
    {
        logoIv.setImage(logoImage);             //logo image regular size
        logoIv.setFitHeight(460);
        logoIv.setFitWidth(460);
        logoIv.setPreserveRatio(true);

        logoIvMini.setImage(logoImage);         //logo image mini size
        logoIvMini.setFitHeight(30);
        logoIvMini.setFitWidth(35);
        logoIvMini.setTranslateX(740);

        logoIvMedium.setImage(logoImage);       //logo image medium size
        logoIvMedium.setFitHeight(300);
        logoIvMedium.setFitWidth(300);
        logoIvMedium.setTranslateY(-380);
        logoIvMedium.setTranslateX(300);

        hBoxTopBase.getChildren().add(logoIvMini);     //hBoxTopBase used for start screen
        hBoxTopBase.setBackground(bg);
        hBoxTopBase.setMinHeight(40);
        hBoxTopBase.setPadding(insetsBase);
        hBoxTopBase.setSpacing(10);
        hBoxTopBase.setMinWidth(800);
        hBoxTopBase.setMaxWidth(800);
        borderPaneBase.setTop(hBoxTopBase);

    }

    public void resetBaseUI()
    {
        hBoxTopBase.getChildren().clear();
        borderPaneBase.getChildren().clear();
    }

}

/**/