package Default;

import Default.UI.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class Main extends Application
{
    private ProductKeyUI productKeyUI = new ProductKeyUI();
    private HomeScreenUI homeScreenUI = new HomeScreenUI();
    private DeletePJUI deletePJUI = new DeletePJUI();
    private CreateNewPJUI createNewPJUI = new CreateNewPJUI();
    private CreateNewPJ2UI createNewPJ2UI;
    private OpenPJUI openPJUI;
    private OpenPJUI2_2 openPJUI2_2 = new OpenPJUI2_2();
    private Scene scene;
    private int result;
    private boolean  groupSet = false ;
    private String userHomePath = System.getProperty("user.home");
    private String pjName, pjAddress, mostRecentWeek;
    private String startDate;
    private Integer month, day, year, employees, projectNum, numOfEmps, weeksAdded;
    private Double pjPrice, profit, materialExpense, employeeExpense;
    private ConstructionProject constructionProject;
    private Group group = new Group();
    private File currentPJFile;
    private ArrayList<String> employeeNameArrayList = new ArrayList<>();
    private boolean inOpen2 = false, resultFromKey, verbose = false;



    @Override
    public void start(Stage primaryStage)
    {
        result = productKeyUI.checkDir();    //checks to see if this is the first time program is run on this machine
        group.setAutoSizeChildren(true);
        scene = new Scene(group);

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        primaryStage.setTitle("Construction Project");
        primaryStage.setScene(scene);
        primaryStage.setX(primaryScreenBounds.getMinX() + 50);
        primaryStage.setY(primaryScreenBounds.getMinY() + 50);
        primaryStage.setWidth(800);
        primaryStage.setHeight(450);
        primaryStage.setResizable(true);
        primaryStage.show();

        /* ****************************************************************************************
         *                                                                                        *
         *                                  LOGIN                                                 *
         *                                                                                        *
         ******************************************************************************************
         */

        if (result == 1) //Product key has previously been entered
        {
            productKeyUI.deleteProductKeyUI();
            group.getChildren().addAll(homeScreenUI.getHSBorderPane());     //set HomeScreen UI to group

        } else if (result == 2)  //Product key has not been entered
        {
            File programFiles = new File(userHomePath + "/ConstructionProject/ProgramFiles");
            File weekInfoFile = new File(userHomePath + "/ConstructionProject/ProgramFiles/WeekInfo");
            File empsInfoFile = new File(userHomePath + "/ConstructionProject/ProgramFiles/Projects");
            File projectFiles = new File(userHomePath + "/ConstructionProject/ProjectFiles");
            File PNFile = new File(userHomePath + "/ConstructionProject/PN.txt");
            File yFile = new File(userHomePath + "/ConstructionProject/y.txt");

            try{
                Scanner y = new Scanner(yFile);
            }
            catch(FileNotFoundException e)
            {
                System.out.println("File not found 90" + e );
            }



            if(! (programFiles.mkdir() && weekInfoFile.mkdir() && projectFiles.mkdir() && empsInfoFile.mkdir()) )
            {
                System.out.println("opps");
            }

            group.getChildren().addAll(productKeyUI.getBorderPane());       //set Product Key UI to group

            productKeyUI.getContinueButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {   //Once user has entered product key
                resultFromKey = productKeyUI.checkKey(productKeyUI.getTextField());  //check if product key matches

                if (!resultFromKey)  //if product key does not match, delete constructionProject folder and exit
                {
                    File customDir = new File(userHomePath + "/ConstructionProject");
                    try {
                        customDir.delete();
                        System.exit(0);
                    } catch (Throwable throwable) {
                        System.out.println("Error has occurred " + throwable);
                    }
                } else   //if key does match set to home screen
                {
                    productKeyUI.deleteProductKeyUI();
                    group.getChildren().addAll(homeScreenUI.getHSBorderPane());     //set HomeScreen UI to group
                }
            });
        } else {
            System.out.println("Error has occurred");
            System.exit(0);
        }   //end of if/else on checkDir result

        /*****************************************************************************************
         *                                                                                        *
         *                                  CREATE NEW PROJECT                                    *
         *                                                                                        *
         ******************************************************************************************
         */


        //user presses create new project from home screen
        homeScreenUI.getNewPJButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //set newPJ screen(input project Info)
            createNewPJUI.setUI();
            if(result == 1)
            {
                if(group.getChildren().contains(homeScreenUI.getHSBorderPane()))
                    group.getChildren().removeAll(homeScreenUI.getHSBorderPane());
            }
            else if (result == 2)
            {
                if(group.getChildren().contains(productKeyUI.getBorderPane()))
                    group.getChildren().removeAll(productKeyUI.getBorderPane());
            }
            else
            {System.out.println("Error has occurred");
             System.exit(0);}

            if(!group.getChildren().contains(createNewPJUI.getNewPjBorderPane()))
                group.getChildren().add(createNewPJUI.getNewPjBorderPane());

            inOpen2 = false;
        });

        //user presses back from create new pj window 1
        createNewPJUI.getBackButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            group.getChildren().remove(createNewPJ2UI);
            group.getChildren().add(homeScreenUI.getHSBorderPane());
            createNewPJUI.removeUI();
        });


        //user presses continue from input project info screen
        createNewPJUI.getContinueButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            TextField[] textField = createNewPJUI.getTextFields();
            for(int i = 0; i < textField.length; i++)
            {
                if(textField[i].getText().trim().isEmpty())
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("You cannot leave any blank text fields");
                    alert.showAndWait();
                    return;
                }
                if(textField[i].getText().indexOf('|') != -1)
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Do not enter any | characters");
                    alert.showAndWait();
                    return;
                }
            }

            if(! (textField[3].getText().matches("-?\\d+(\\.\\d+)?")  && textField[4].getText().matches("-?\\d+(\\.\\d+)?") &&
                    textField[5].getText().matches("-?\\d+(\\.\\d+)?") && textField[6].getText().matches("-?\\d+(\\.\\d+)?") &&
                    textField[7].getText().matches("-?\\d+(\\.\\d+)?") ) )
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("You must enter numerical values for\nDates, Price, and Employee number");
                alert.showAndWait();
                return;
            }
            if(Integer.parseInt(textField[7].getText()) <= 0)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("You must have at least one employee");
                alert.showAndWait();
                return;
            }
            if(Integer.parseInt(textField[3].getText()) <= 0 || Integer.parseInt(textField[3].getText()) > 12)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Month must be between 0 and 12");
                alert.showAndWait();
                return;
            }
            if(Integer.parseInt(textField[4].getText()) <= 0 || Integer.parseInt(textField[4].getText()) > 31)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Day must be between 0 and 31");
                alert.showAndWait();
                return;
            }
            if(Integer.parseInt(textField[5].getText()) <= 1990 || Integer.parseInt(textField[3].getText()) > 2100)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Year must be four digits");
                alert.showAndWait();
                return;
            }
            if(Double.parseDouble(textField[6].getText()) < 1)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("You project price must be at least 1");
                alert.showAndWait();
                return;
            }

            File folder = new File(userHomePath + "/ConstructionProject/ProjectFiles");
            File[] listOfFiles = folder.listFiles();

            for(File listofFile : listOfFiles)
            {
                if(listofFile.getName().equals(textField[0].getText() + ".txt"))
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("A file with that name already exists");
                    alert.showAndWait();
                    return;
                }
            }

            pjName = textField[0].getText();        //intake all the info from intake project info screen
            pjAddress = textField[1].getText();
            month = Integer.parseInt(textField[3].getText());
            day = Integer.parseInt(textField[4].getText());
            year = Integer.parseInt(textField[5].getText());
            pjPrice = Double.parseDouble(textField[6].getText());
            employees = Integer.parseInt(textField[7].getText());
            startDate =  (month >=10 ? String.valueOf(month) : "0" + String.valueOf(month))  + "/" +
                         (day >= 10  ? String.valueOf(day)   : "0" + String.valueOf(day))    + "/" + String.valueOf(year) ;
            projectNum = getPjNum();

            //instantiate constructionPJ obj and set all the values taken from user input from PJ Info Input screen
            constructionProject = new ConstructionProject(employees);
            constructionProject.setProjectAddress(pjAddress);
            constructionProject.setProjectName(pjName);
            constructionProject.setGrossPayForProject(pjPrice);
            constructionProject.setProjectStartDate(startDate);
            constructionProject.setProjectNum(projectNum);

            //clear textfields
            for (TextField aTextField : textField) {
                aTextField.clear();
            }
            createNewPJ2UI = new CreateNewPJ2UI(employees);
            group.getChildren().removeAll(createNewPJUI.getNewPjBorderPane());
            group.getChildren().add(createNewPJ2UI.getCreateNewPJ2BorderP());   //add Intake EmployeeInfo UI to group


            createNewPJ2UI.getBackButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event2 -> {
                group.getChildren().remove(createNewPJ2UI.getCreateNewPJ2BorderP());
                group.getChildren().add(createNewPJUI.getNewPjBorderPane());
                createNewPJUI.setUI();
            });

            //user presses continue from inputting employee names
            createNewPJ2UI.getContinueButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
                for (int i = 0; i < employees; i++)    //store employee names into employee objects
                {
                    constructionProject.getEmployeeArrayList().get(i).setEmployeeName(createNewPJ2UI.getTextFieldVector().get(i).getText());
                }

                writePJToFile(constructionProject);        //write project info into file

                //clear textfields
                for (TextField aTextField1 : createNewPJ2UI.getTextFieldVector()) {
                    aTextField1.clear();
                }

                for(int i = 0; i < textField.length; i++)
                {
                    textField[i].clear();
                }


                //reset string variables
                pjName = null;
                projectNum = null;
                month = null;
                day = null;
                year = null;
                pjPrice = null;
                employees = null;
                startDate = null;

                writeEmpInfo(constructionProject);

                groupSet = true;
                group.getChildren().removeAll(createNewPJ2UI.getCreateNewPJ2BorderP());
                group.getChildren().addAll(homeScreenUI.getHSBorderPane());
                createNewPJUI.resetTextfields();
            });

        });//end of ser presses continue from input project info screen EventHandler


         /*****************************************************************************************
         *                                                                                        *
         *                                  OPEN PROJECT                                          *
         *                                                                                        *
         ******************************************************************************************/
        openPJUI = new OpenPJUI();
        //user presses open existing project from home screen
        homeScreenUI.getOpenPJButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            //check if group has children if it does delete it
            openPJUI.refreshListOfFiles();
            if(groupSet)
            {
                group.getChildren().removeAll(createNewPJ2UI.getCreateNewPJ2BorderP());
            }else
            {
                homeScreenUI.deleteHomeScreenUI();
            }
            group.getChildren().remove(homeScreenUI.getHSBorderPane());
            group.getChildren().addAll(openPJUI.getBorderPane());
        });

        //user presses back from open project window
        openPJUI.getBackButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            group.getChildren().removeAll(openPJUI.getBorderPane());
            group.getChildren().add(homeScreenUI.getHSBorderPane());
            openPJUI.getComboBox().getSelectionModel().clearSelection();
            openPJUI.getContinueButton().setDisable(true);
            homeScreenUI.resetHomeScreenUI();
        });

        //user presses continue after selecting file to open
        openPJUI.getContinueButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            currentPJFile = new File(userHomePath + "/ConstructionProject/ProjectFiles/" + openPJUI.getComboBox().getValue());
            if(verbose)
                System.out.println("currentPjFile: " + currentPJFile.getPath());

            if(!currentPJFile.exists())
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Critical Error has occurred\nClosing program");
                alert.showAndWait();
                System.exit(0);
            }
            readFromFile(currentPJFile); //converts from psv and stores into global variables

            //set values to object
            constructionProject = new ConstructionProject(employeeNameArrayList.size());
            constructionProject.setProjectName(pjName);
            constructionProject.setGrossPayForProject(pjPrice);
            constructionProject.setProjectStartDate(startDate);
            constructionProject.setProjectNum(projectNum);
            constructionProject.setMostRecentWeek(mostRecentWeek);

            for (int i = 0; i < employeeNameArrayList.size(); i++) {
                constructionProject.getEmployeeArrayList().get(i).setEmployeeName(employeeNameArrayList.get(i));
            }

            primaryStage.setX(primaryScreenBounds.getMinX() + 50);
            primaryStage.setY(primaryScreenBounds.getMinY() + 50);
            primaryStage.setWidth(1300);
            primaryStage.setHeight(700);

            group.getChildren().removeAll(openPJUI.getBorderPane());
            openPJUI2_2.setConstructionproject(constructionProject);
            group.getChildren().addAll(openPJUI2_2.getBorderPane());
            inOpen2 = true;

            openPJUI.getComboBox().getSelectionModel().clearSelection();
            openPJUI.getContinueButton().setDisable(true);


        });


        //user press Home Button
        openPJUI2_2.getHomeButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            writePJToFile(constructionProject);
            openPJUI2_2.writeWeekInfoToFile(constructionProject);
            openPJUI2_2.writeEmpEarnInfo(constructionProject);
            group.getChildren().remove(openPJUI2_2.getBorderPane());
            homeScreenUI.resetHomeScreenUI();
            group.getChildren().add(homeScreenUI.getHSBorderPane());
            primaryStage.setWidth(800);
            primaryStage.setHeight(450);
            employeeNameArrayList.clear();
            openPJUI2_2.resetOpenPJUI_2();
        });

        /*****************************************************************************************
         *                                                                                        *
         *                                  DELETE PROJECT                                        *
         *                                                                                        *
         ******************************************************************************************/

        homeScreenUI.getDeleteProjectButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                 deletePJUI.refreshListOfFiles();

                 if(group.getChildren().contains(homeScreenUI.getHSBorderPane()))
                     group.getChildren().remove(homeScreenUI.getHSBorderPane());

                 if(!group.getChildren().contains(deletePJUI.getBorderPane()))
                     group.getChildren().add(deletePJUI.getBorderPane());
            }
        });

        deletePJUI.getBackButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            group.getChildren().remove(deletePJUI.getBorderPane());
            group.getChildren().add(homeScreenUI.getHSBorderPane());
            deletePJUI.getComboBox().getSelectionModel().clearSelection();
            deletePJUI.getContinueButton().setDisable(true);
        });

        deletePJUI.getContinueButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            String emps, week, value = deletePJUI.getComboBox().getValue().toString();
            StringBuilder sb = new StringBuilder(value);
            sb.delete(sb.length() - 4, sb.length());
            emps = userHomePath + "/ConstructionProject/ProgramFiles/Projects/" + sb.toString();
            emps += "Emps";
            week = userHomePath + "/ConstructionProject/ProgramFiles/WeekInfo/" + sb.toString();
            week += " Week Info.txt";

            File weekFile = new File(week);
            File empFile = new File(emps);
            File projectFile = new File(userHomePath + "/ConstructionProject/ProjectFiles/" + value);

            System.out.println("week file: " + weekFile.getAbsolutePath() + " empFile: " + empFile.getAbsolutePath() + " Project File: " + projectFile.getAbsolutePath());

            deleteDirectory(empFile);
            projectFile.delete();
            weekFile.delete();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Project has been successfully deleted");
            alert.showAndWait();

            group.getChildren().remove(deletePJUI.getBorderPane());
            group.getChildren().add(homeScreenUI.getHSBorderPane());
            deletePJUI.getComboBox().getSelectionModel().clearSelection();
            deletePJUI.getContinueButton().setDisable(true);
        });



        /*****************************************************************************************
         *                                                                                        *
         *                                  PROCESS WEEK                                          *
         *                                                                                        *
         ******************************************************************************************/

        //user presses process new week from open pj 2 window
        openPJUI2_2.getProcessNewWeekButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            openPJUI2_2.processNewWeek();
        });

        //user presses continue from process new week window
        openPJUI2_2.getContinueButtonFromPW().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to continue");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK)
        {
            openPJUI2_2.pressedContinueFromProcessNewWindow();
        }
        });

        //user presses back from process new week window
        openPJUI2_2.getBackFromProcessWeekButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            openPJUI2_2.removeProcessWeek();
            openPJUI2_2.addWeekTextScrollPane();
            openPJUI2_2.updateWeekInfoScrollPane();
        });


        /*****************************************************************************************
         *                                                                                        *
         *                                  REMOVE WEEK                                           *
         *                                                                                        *
         ******************************************************************************************/

        //user presses remove week
        openPJUI2_2.getRemoveWeekButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            openPJUI2_2.openRemoveWeekWindow();
        });

        //user presses remove week after selecting week to remove
        openPJUI2_2.getRemoveWeekButton2().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if(openPJUI2_2.getComboBoxWeekToErase().getValue() != null)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Are you sure you want to remove " + openPJUI2_2.getComboBoxWeekToErase().getValue());
                Optional<ButtonType> result = alert.showAndWait();

                if(result.isPresent() && result.get() == ButtonType.OK)
                {
                 //calls remove week function from construction project from within OpenPJUI2. Gives it the index of week selected to be removed
                 openPJUI2_2.removeWeek(openPJUI2_2.getComboBoxWeekToErase().getSelectionModel().getSelectedItem().toString());
                }
            }
        });

        //user presses back from remove week
        openPJUI2_2.getBackFromRemoveWeekButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            openPJUI2_2.closeRemoveWeekWindow();
            openPJUI2_2.updateWeekInfoScrollPane();
        });



        /*****************************************************************************************
         *                                                                                        *
         *                                  VIEW EMPLOYEES                                        *
         *                                                                                        *
         ******************************************************************************************/

        //User presses view Emp from Main Program Window
        openPJUI2_2.getViewEmpInfoButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            openPJUI2_2.setViewEmpWindow();
        });

        //user presses back from view emp window
        openPJUI2_2.getBackFromViewEmpButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            openPJUI2_2.removeViewEmpWindow();
            openPJUI2_2.addButtonsVBox();
            openPJUI2_2.addChart();
            openPJUI2_2.addWeekTextScrollPane();
            openPJUI2_2.updateWeekInfoScrollPane();
        });

        //user presses add employee from view employee week
        openPJUI2_2.getAddEmployeeButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            VBox diaogVbox = new VBox(20);
            diaogVbox.setPadding(new Insets(10,10,10,10));
            TextField empname = new TextField();
            Button continueButton = new Button("Continue");
            diaogVbox.getChildren().addAll(new Text("Enter new employee's name"), empname, continueButton);
            Scene dialogScene = new Scene(diaogVbox, 300, 200);
            dialog.setScene(dialogScene);
            dialog.show();

            continueButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
                openPJUI2_2.getConstructionProject().getEmployeeArrayList().add(new Employee(empname.getText()));
                dialog.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Employee was successfully added");
                alert.showAndWait();

            });

        });

    }//end of start

    /*****************************************************************************************
     *                                                                                        *
     *                                  FUNCTIONS                                             *
     *                                                                                        *
     ******************************************************************************************/


    public static boolean deleteDirectory(File directory) {
        if(directory.exists()){
            File[] files = directory.listFiles();
            if(null!=files){
                for(int i=0; i<files.length; i++) {
                    if(files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    }
                    else {
                        files[i].delete();
                    }
                }
            }
        }
        return(directory.delete());
    }

    private void readFromFile(JarEntry currentEntry, JarFile jarFile)
    {
        int lineCounter = 0, elementCounter = 0, pipePosition = 0, lastPipePosition = 0;
        String currentLineString, currentSubString;

        try {                   //convert psv files into sub strings (used to populate Cons PJ Object)
            InputStream inputStream = jarFile.getInputStream(currentEntry);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder out = new StringBuilder();

            while( (currentLineString = reader.readLine()) != null )
            {
                System.out.println("Current Line: " + currentLineString);

                while (pipePosition != -1) {
                    pipePosition = currentLineString.indexOf('|', lastPipePosition + 1);
                    if (elementCounter == 0 && pipePosition != -1)    //first time around line
                    {
                        currentSubString = currentLineString.substring(0, pipePosition);
                        if (lineCounter == 0)                            //if it is first line (PJ info)
                        {
                            projectNum = Integer.valueOf(currentSubString);
                        }
                        else if (lineCounter == 1)      //if it is second line (PJ info2)
                        {
                            profit = Double.valueOf(currentSubString);
                        }
                        else if(lineCounter > 1)                    //if it is not first time (Employee Info)
                        {
                            employeeNameArrayList.add(currentSubString);
                        }
                    } else if (elementCounter >= 1 && pipePosition != -1) {     //second time around PJ Name
                        currentSubString = currentLineString.substring(lastPipePosition + 1, pipePosition);
                        if (lineCounter == 0 && elementCounter == 1)                        //if it is first time (PJ info), and 2nd element
                        {
                            pjAddress = currentSubString;

                        }
                        else if (lineCounter == 0 && elementCounter == 2)        //if it is not first time(Employee Info), and 3rd element
                        {
                            pjName = currentSubString;
                        }
                        else if (lineCounter == 0 && elementCounter == 3)
                        {
                            startDate= currentSubString;
                        }
                        else if (lineCounter == 1 && elementCounter == 1)
                        {
                            materialExpense = Double.valueOf(currentSubString);
                        }
                        else if (lineCounter == 1 && elementCounter == 2)
                        {
                            employeeExpense = Double.valueOf(currentSubString);
                        }
                        else if(lineCounter == 1 && elementCounter == 3)
                        {
                            numOfEmps = Integer.valueOf(currentSubString);
                        }
                        else if(lineCounter == 1 && elementCounter == 4)
                        {
                            mostRecentWeek = currentSubString;
                        }
                        else if (lineCounter >= 2)
                        {
                            employeeNameArrayList.add(currentSubString);
                        }
                    } else if (pipePosition == -1)
                    {
                        if(elementCounter > 0)
                            currentSubString = currentLineString.substring(lastPipePosition+1);
                        else
                            currentSubString = currentLineString.substring(lastPipePosition);
                        if(lineCounter == 0)
                        {
                            pjPrice = Double.valueOf(currentSubString);
                        }
                        else if(lineCounter == 1)
                        {
                            weeksAdded = Integer.valueOf(currentSubString);
                        }
                        else if(lineCounter >= 2)
                        {
                            employeeNameArrayList.add(currentSubString);
                        }
                    }
                    elementCounter++;
                    lastPipePosition = pipePosition;
                }
                lastPipePosition = 0;
                elementCounter = 0;
                pipePosition = 0;
                lineCounter++;
            }

        } catch (Throwable throwable) {
        }
    }

    private void readFromFile( File currentPJFile)
    {
        int lineCounter = 0, elementCounter = 0, pipePosition = 0, lastPipePosition = 0;
        String currentLineString, currentSubString;

        try {                   //convert psv files into sub strings (used to populate Cons PJ Object)
            Scanner input = new Scanner(currentPJFile);

            while (input.hasNext()) {
                currentLineString = input.nextLine();
                while (pipePosition != -1) {
                    pipePosition = currentLineString.indexOf('|', lastPipePosition + 1);
                    if (elementCounter == 0 && pipePosition != -1)    //first time around line
                    {
                        currentSubString = currentLineString.substring(0, pipePosition);
                        if (lineCounter == 0)                            //if it is first line (PJ info)
                        {
                            projectNum = Integer.valueOf(currentSubString);
                        }
                        else if (lineCounter == 1)      //if it is second line (PJ info2)
                        {
                            profit = Double.valueOf(currentSubString);
                        }
                        else if(lineCounter > 1)                    //if it is not first time (Employee Info)
                        {
                            employeeNameArrayList.add(currentSubString);
                        }
                    } else if (elementCounter >= 1 && pipePosition != -1) {     //second time around PJ Name
                        currentSubString = currentLineString.substring(lastPipePosition + 1, pipePosition);
                        if (lineCounter == 0 && elementCounter == 1)                        //if it is first time (PJ info), and 2nd element
                        {
                            pjAddress = currentSubString;

                        }
                        else if (lineCounter == 0 && elementCounter == 2)        //if it is not first time(Employee Info), and 3rd element
                        {
                            pjName = currentSubString;
                        }
                        else if (lineCounter == 0 && elementCounter == 3)
                        {
                            startDate= currentSubString;
                        }
                        else if (lineCounter == 1 && elementCounter == 1)
                        {
                            materialExpense = Double.valueOf(currentSubString);
                        }
                        else if (lineCounter == 1 && elementCounter == 2)
                        {
                            employeeExpense = Double.valueOf(currentSubString);
                        }
                        else if(lineCounter == 1 && elementCounter == 3)
                        {
                            numOfEmps = Integer.valueOf(currentSubString);
                        }
                        else if(lineCounter == 1 && elementCounter == 4)
                        {
                            mostRecentWeek = currentSubString;
                        }
                        else if (lineCounter >= 2)
                        {
                            employeeNameArrayList.add(currentSubString);
                        }
                    } else if (pipePosition == -1)
                    {
                        if(elementCounter > 0)
                            currentSubString = currentLineString.substring(lastPipePosition+1);
                        else
                            currentSubString = currentLineString.substring(lastPipePosition);
                        if(lineCounter == 0)
                        {
                            pjPrice = Double.valueOf(currentSubString);
                        }
                        else if(lineCounter == 1)
                        {
                            weeksAdded = Integer.valueOf(currentSubString);
                        }
                        else if(lineCounter >= 2)
                        {
                            employeeNameArrayList.add(currentSubString);
                        }
                    }
                    elementCounter++;
                    lastPipePosition = pipePosition;
                }
                lastPipePosition = 0;
                elementCounter = 0;
                pipePosition = 0;
                lineCounter++;
            }
        } catch (Throwable throwable) {
        }
    }

    private void writeEmpInfo(ConstructionProject constructionProject)
    {   //will create project's folder inside Projects Folder(to be used when project is created)
        try
        {
            String path = userHomePath + "/ConstructionProject/ProgramFiles/Projects/" + constructionProject.getProjectName() + "Emps";
            File projectDir = new File(path);
            File outputFile;

            if (projectDir.exists())
            {

            }
            else if (projectDir.mkdir())
            {
                //Create a empty file for each employee
                for(int i =0; i < constructionProject.getEmployeeArrayList().size(); i++)
                {
                    outputFile = new File(path + "//" + constructionProject.getEmployeeArrayList().get(i).getEmployeeName() + ".txt");
                    PrintWriter writer = new PrintWriter(outputFile);
                }
            }
        }catch (IOException e)
        {

        }
    }

    private void writePJToFile(ConstructionProject constructionProject)       //method used to write project info to file
    {
        String filename = constructionProject.getProjectName();
        try {                                                                                 //write project info to file
            File outputFile = new File(userHomePath + "/ConstructionProject/ProjectFiles/" + filename + ".txt");
            PrintWriter writer = new PrintWriter(outputFile);
            if (!outputFile.exists())
            {
                System.out.println("Output file does not exist: " + outputFile.getPath() + " name: " + outputFile.getName());
                System.exit(0);
            }
            writer.print(constructionProject.getProjectNum());
            writer.print('|');
            writer.print(constructionProject.getProjectAddress() + '|');
            writer.print(constructionProject.getProjectName() + '|');
            writer.print(constructionProject.getProjectStartDate() + '|');
            writer.print(constructionProject.getGrossPayForProject());
            writer.println();

            writer.print(constructionProject.getCurrentProfit());
            writer.print('|');
            writer.print(constructionProject.getTotalMaterialExpense());
            writer.print('|');
            writer.print(constructionProject.getTotalEmployeeExpense());
            writer.print('|');
            writer.print(constructionProject.getEmployeeArrayList().size());
            writer.print('|');
            writer.print(constructionProject.getMostRecentWeek());
            writer.print('|');
            writer.print(constructionProject.getWeeksAdded());
            writer.println();

            for (int i = 0, lineI = 0; i < constructionProject.getEmployeeArrayList().size(); i++, lineI++)        //write employee info to file
            {
                if (i == constructionProject.getEmployeeArrayList().size() - 1) {                                   //used to write last element of file without Pipe
                    writer.print(constructionProject.getEmployeeArrayList().get(i).getEmployeeName());

                } else if (lineI >= 3)                         //used to write last element of line without Pipe
                {
                    writer.print(constructionProject.getEmployeeArrayList().get(i).getEmployeeName());
                    writer.println();
                    lineI = -1;
                } else {
                    writer.print(constructionProject.getEmployeeArrayList().get(i).getEmployeeName() + '|');    //write to file with pipe
                }
            }
            writer.close();
        } catch (IOException e) {
        }

    }

    private int getPjNum()
    {
        String path = userHomePath + "/ConstructionProject/PN.txt";
        File file = new File(path);
        int pjNum = 1;

        try{
            if (file.exists())
            {
                Scanner input = new Scanner(file);
                pjNum = input.nextInt();
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.print(pjNum + 1);
                printWriter.close();

            } else
            {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(1);
                pjNum = 1;
                printWriter.close();
            }
        }catch (Throwable throwable)
        {

        }

        return pjNum;
    }

    @Override
    public void stop()
    {
        if(inOpen2)
        {
            writePJToFile(constructionProject);
            openPJUI2_2.writeWeekInfoToFile(constructionProject);
            openPJUI2_2.writeEmpEarnInfo(constructionProject);
        }
    }

    public static void main (String [] args)
    {
        Application.launch();
    }
}
