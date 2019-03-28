package Default.UI;

import Default.ConstructionProject;
import Default.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.chart.PieChart;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Description:
 * Programmer : Braulio Duarte
 * Project/Lab: Programming Exercise
 * Last Modified: 3/13/17.
 */
public class OpenPJUI2_2 extends BaseUI{

    private BorderPane borderPaneOPJ2 = new BorderPane();
    private GridPane centerGridPane = new GridPane();
    private GridPane processWeekGridPane = new GridPane();
    private TableView viewEmpTableView = new TableView();
    private TableView weekInfoTableView = new TableView();
    private ScrollPane viewEmpScrollPane = new ScrollPane();
    private ScrollPane weekInfoScrollPane = new ScrollPane();
    private ScrollPane processWeekScrollPane = new ScrollPane();
    private VBox vBoxHeader = new VBox();
    private VBox buttonsVBox = new VBox();
    private VBox weeksVbox = new VBox();
    private VBox vBoxRemoveWeek = new VBox();
    private VBox vBoxWeekTextInfo = new VBox();
    private VBox vBoxButtonsViewEmp = new VBox();
    private Insets insets1 = new Insets(10, 10, 10, 10);
    private Button processNewWeekButton = new Button("Process New Week");
    private Button removeWeekButton = new Button("Remove Week");
    private Button removeWeekButton2 = new Button("Remove Week");
    private Button viewEmpInfoButton = new Button("View Employees");
    private Button continueButtonFromPW = new Button("Continue");
    private Button homeButton = new Button("Home");
    private Button saveButton = new Button("Save");
    private Button addEmployeeButton = new Button("Add Employee");
    private Button backFromViewEmpButton = new Button("Back");
    private Button backFromProcessWeekButton = new Button("Back");
    private Button backFromRemoveWeekButton = new Button("Back");
    private ListView topListView = new ListView();
    private ListView topListViewData = new ListView();
    private PieChart chart;
    private ConstructionProject constructionProject;
    private final ObservableList headers = FXCollections.observableArrayList();
    private final ObservableList data = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> pieChartData;
    private ArrayList<Text> textArrayList = new ArrayList<>();
    private ArrayList<TextField> fieldArrayList = new ArrayList<>();
    private ArrayList<Text> weekTextsArrayList = new ArrayList<>();
    private TextField expenseField = new TextField();
    private TextField startPeriodField = new TextField();
    private TextField endPeriodField = new TextField();
    private Text expenseText = new Text("Material Expenses");
    private Text expenseHeaderText = new Text("\tEnter employees expense and material expenses for the week");
    private Text startPeriodText = new Text("Enter the week's start date");
    private Text endPeriodText = new Text("Enter the week's end date");
    private Text removeWeekText = new Text("Select week to be removed:");
    private Text weekTextHeader = new Text("Weeks Processed");
    private ComboBox comboBoxWeekToErase = new ComboBox();
    TableColumn weekNumCl = new TableColumn("Week Number");
    TableColumn employeeExpCl = new TableColumn("Employee Expense");
    TableColumn materialExpCl = new TableColumn("Material Expense");
    TableColumn startDateCl = new TableColumn("Start Date");
    TableColumn endDateCl = new TableColumn("End Date");


    public OpenPJUI2_2()
    {

    }

    /*****************************************************************************************
     *                                                                                        *
     *                                  SET / UPDATE / REMOVE                                 *
     *                                                                                        *
     ******************************************************************************************/

    public void setConstructionproject(ConstructionProject constructionproject)
    {
        this.constructionProject = constructionproject;

        readWeekInfo(constructionProject);
        readEmpEarnInfo(constructionproject);

        //top border(changed from BASE UI
        hBoxTopBase.setMinWidth(1300);
        logoIvMini.setTranslateX(1240);

        vBoxHeader.getChildren().add(hBoxTopBase);
        borderPaneOPJ2.setTop(vBoxHeader);
        setHeaders();
        vBoxHeader.getChildren().addAll(topListView, topListViewData);

        buttonsVBox.getChildren().add(processNewWeekButton);

        removeWeekButton.setMinWidth(137);
        buttonsVBox.getChildren().add(removeWeekButton);

        viewEmpInfoButton.setMinWidth(137);
        buttonsVBox.getChildren().add(viewEmpInfoButton);

        saveButton.setMinWidth(80);
        buttonsVBox.getChildren().add(saveButton);

        homeButton.setMinWidth(80);
        buttonsVBox.getChildren().add(homeButton);

        buttonsVBox.setPadding(insets1);
        buttonsVBox.setSpacing(10);
        buttonsVBox.setMinWidth(280);
        addButtonsVBox();

        weekInfoTableView.setMinWidth(548);
        weekTextHeader.setTranslateX(200);

        weekInfoScrollPane.setMinWidth(550);
        weekInfoScrollPane.setMinHeight(400);
        weekInfoScrollPane.setContent(vBoxWeekTextInfo);


        weeksVbox.setSpacing(10);
        weeksVbox.setPadding(insets1);
        addWeekTextScrollPane();
        updateWeekInfoScrollPane();
        addChart();

        borderPaneOPJ2.setCenter(centerGridPane);

    }

    public void openRemoveWeekWindow()
    {
        removeButtonsVbox();
        removeChart();
        weeksVbox.setTranslateX(0);

        vBoxRemoveWeek.getChildren().addAll(removeWeekText, comboBoxWeekToErase, removeWeekButton2, backFromRemoveWeekButton);
        vBoxRemoveWeek.setSpacing(10);
        vBoxRemoveWeek.setPadding(insets1);
        centerGridPane.add(vBoxRemoveWeek, 3, 0);

        for(int i = 0; i < constructionProject.getWeekInfoArrayList().size(); i++)
        {
            comboBoxWeekToErase.getItems().add("Week " + constructionProject.getWeekInfoArrayList().get(i).getWeekNum());
        }

    }
    public void closeRemoveWeekWindow()
    {
        if(centerGridPane.getChildren().contains(vBoxRemoveWeek))
            centerGridPane.getChildren().remove(vBoxRemoveWeek);
        if(vBoxRemoveWeek.getChildren().contains(removeWeekText))
            vBoxRemoveWeek.getChildren().remove(removeWeekText);
        if(vBoxRemoveWeek.getChildren().contains(comboBoxWeekToErase))
            vBoxRemoveWeek.getChildren().remove(comboBoxWeekToErase);
        if(vBoxRemoveWeek.getChildren().contains(removeWeekButton2))
            vBoxRemoveWeek.getChildren().remove(removeWeekButton2);
        if(vBoxRemoveWeek.getChildren().contains(backFromRemoveWeekButton))
            vBoxRemoveWeek.getChildren().remove(backFromRemoveWeekButton);

        comboBoxWeekToErase.getItems().clear();

        addButtonsVBox();
        addChart();
    }

    public void removeWeek(String selection)
    {
        int weekNum = Integer.valueOf(selection.substring(5));
        constructionProject.removeWeekInfo(weekNum);
        writeWeekInfoToFile(constructionProject);
        writeEmpEarnInfo(constructionProject);
        closeRemoveWeekWindow();
        updateWeekInfoScrollPane();
    }

    public void addButtonsVBox()
    {
        buttonsVBox.setTranslateY(20);
        if(!centerGridPane.getChildren().contains(buttonsVBox))
            centerGridPane.add(buttonsVBox,0,0);
    }

    public void removeButtonsVbox()
    {
        if(centerGridPane.getChildren().contains(buttonsVBox))
            centerGridPane.getChildren().remove(buttonsVBox);
    }

    public void addWeekTextScrollPane()
    {
        weekInfoScrollPane.setContent(weekInfoTableView);
        weekNumCl.setMinWidth(60);
        employeeExpCl.setMinWidth(60);
        materialExpCl.setMinWidth(60);
        startDateCl.setMinWidth(60);
        endDateCl.setMinWidth(60);

        weekInfoTableView.getColumns().addAll(weekNumCl,employeeExpCl,materialExpCl,startDateCl,endDateCl);
        weeksVbox.setTranslateX(-40);

        if(!weeksVbox.getChildren().contains(weekTextHeader))
            weeksVbox.getChildren().add(weekTextHeader);
        if(!weeksVbox.getChildren().contains(weekInfoScrollPane))
            weeksVbox.getChildren().add(weekInfoScrollPane);
        if(!centerGridPane.getChildren().contains(weeksVbox))
            centerGridPane.add(weeksVbox, 2, 0);

    }

    public void removeWeekInfoScrollPane()
    {
        weekInfoTableView.getColumns().clear();
        if(centerGridPane.getChildren().contains(weeksVbox))
           centerGridPane.getChildren().remove(weeksVbox);
    }

    public void updateWeekInfoScrollPane()
    {
        final ObservableList<ConstructionProject.WeekInfo> data = FXCollections.observableArrayList();

        for(int i = 0; i < constructionProject.getWeekInfoArrayList().size(); i++)
        {
            data.add(constructionProject.getWeekInfoArrayList().get(i));
            weekNumCl.setCellValueFactory (new PropertyValueFactory<ConstructionProject.WeekInfo, String>("weekNum"));
            employeeExpCl.setCellValueFactory(new PropertyValueFactory<ConstructionProject.WeekInfo, String>("empExpense"));
            materialExpCl.setCellValueFactory(new PropertyValueFactory<ConstructionProject.WeekInfo, String>("materialExpense"));
            startDateCl.setCellValueFactory(new PropertyValueFactory<ConstructionProject.WeekInfo, String>("startDate"));
            endDateCl.setCellValueFactory(new PropertyValueFactory<ConstructionProject.WeekInfo, String>("endDate"));
        }

        weekInfoTableView.setItems(data);
    }
    public void removeChart()
    {
        if(centerGridPane.getChildren().contains(chart))
            centerGridPane.getChildren().remove(chart);
    }

    public void addChart()
    {
        pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Profit", constructionProject.getCurrentProfit()),
                        new PieChart.Data("Material", constructionProject.getTotalMaterialExpense()),
                        new PieChart.Data("Employee", constructionProject.getTotalEmployeeExpense())
                );

        chart = new PieChart(pieChartData)
        {
            @Override
            protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight)
            {
                if(getLabelsVisible())
                {
                    getData().forEach(d -> {
                        Optional<Node> opTextNode = chart.lookupAll(".chart-pie-label").stream().filter(n -> n instanceof Text && ((Text) n).getText().contains(d.getName())).findAny();
                        if (opTextNode.isPresent())
                        {
                            ((Text) opTextNode.get()).setText(d.getName() + " " + d.getPieValue());
                        }
                    });
                }
                super.layoutChartChildren(top, left, contentWidth, contentHeight);
            }
        };
        chart.setTitle("Current Profit: $" + constructionProject.getCurrentProfit() );
        chart.setTranslateY(-50);
        chart.setTranslateX(-60);
        chart.setMaxHeight(240);
        chart.setMinHeight(240);
        chart.setMaxWidth(500);
        chart.setMinWidth(500);
        centerGridPane.add(chart, 3, 0);

    }

    public void removeProcessWeek()
    {
        for (int i = 0; i < constructionProject.getEmployeeArrayList().size(); i++) {
            processWeekGridPane.getChildren().removeAll(textArrayList.get(i), fieldArrayList.get(i));
        }
        processWeekGridPane.getChildren().removeAll(expenseText, expenseField,
                startPeriodText, startPeriodField, endPeriodText, endPeriodField );
        centerGridPane.getChildren().removeAll(processWeekScrollPane, expenseHeaderText, backFromProcessWeekButton, continueButtonFromPW);
        if(!centerGridPane.getChildren().contains(buttonsVBox))
            centerGridPane.add(buttonsVBox,0,0);
        addChart();
        addButtonsVBox();

        vBoxWeekTextInfo.getChildren().clear();
        weekTextsArrayList.clear();
    }

    public void setHeaders()
    {
        //                       1         2         3         4         5
        //              1234567890123456789012345678901234567890123456789012345678          1234567890123456789012345678901234567890123456789012345678
        headers.addAll("                 Project Name                   "/*58*/, "                Project Start Date              "/*58*/,
                "                 Project Price                  "/*58*/, "                 Project Number                 "/*58*/,
                "                 Number of Employees               "/*58*/);
        topListView.setOrientation(Orientation.HORIZONTAL);
        topListView.setMinWidth(1200);
        topListView.setItems(headers);
        topListView.setMaxHeight(30);
        topListView.setMinHeight(30);
        topListView.setEditable(false);

        String n = "", d = "", p = "", num = "", e = "";

        for(int i = 0; i < 31 - constructionProject.getProjectName().length(); i++)
        {
            n += " ";
        }
        for(int i = 0; i < 31 - constructionProject.getProjectStartDate().length(); i++)
        {
            d += " ";
        }
        for(int i = 0; i < 31 - Double.toString(constructionProject.getGrossPayForProject()).length() ; i++)
        {
            p += " ";
        }
        for(int i = 0; i < 36 - Integer.toString(constructionProject.getProjectNum()).length(); i++)
        {
            num += " ";
        }
        for(int i = 0; i < 36 - Integer.toString(constructionProject.getEmployeeArrayList().size()).length(); i++)
        {
            e += " ";
        }

        data.addAll("                      " + constructionProject.getProjectName() + n, "                     " + constructionProject.getProjectStartDate() + d,
                "                      " + constructionProject.getGrossPayForProject() + p, "                      " + constructionProject.getProjectNum() + num,
                "                      " + constructionProject.getEmployeeArrayList().size() + e);

        topListViewData.setOrientation(Orientation.HORIZONTAL);
        topListViewData.setMinWidth(1200);
        topListViewData.setItems(data);
        topListViewData.setMaxHeight(30);
        topListViewData.setMinHeight(30);
        topListViewData.setEditable(false);
    }

    /*****************************************************************************************
     *                                                                                        *
     *                                  VIEW EMPLOYEES                                        *
     *                                                                                        *
     ******************************************************************************************/

    public void setViewEmpWindow()
    {
        removeButtonsVbox();
        removeChart();
        removeWeekInfoScrollPane();
        final ObservableList<Employee.empWeekInfo> data = FXCollections.observableArrayList();

        TableColumn empNameCol = new TableColumn("Employee Name");
        TableColumn weekNumCol = new TableColumn("Week Number");
        TableColumn amountCol = new TableColumn("Amount Earned");
        TableColumn startWeekCol = new TableColumn("Start Week");
        TableColumn endWeekCol = new TableColumn("End Week");

        empNameCol.setMinWidth(125);
        weekNumCol.setMinWidth(125);
        amountCol.setMinWidth(125);
        startWeekCol.setMinWidth(125);
        endWeekCol.setMinWidth(125);

        viewEmpTableView.getColumns().addAll(empNameCol, weekNumCol ,amountCol,startWeekCol,endWeekCol);
        viewEmpScrollPane.setContent(viewEmpTableView);
        viewEmpScrollPane.setMinWidth(625);
        viewEmpTableView.setMinWidth(625);
        centerGridPane.add(viewEmpScrollPane,0,0);

        for(int i = 0; i < constructionProject.getEmployeeArrayList().size(); i++)
        {
            for (int y = 0; y < constructionProject.getEmployeeArrayList().get(i).getEmpWeekInfoArrayList().size(); y++)
            {
                if (constructionProject.getEmployeeArrayList().get(i).getEmpWeekInfoArrayList().get(y).getAmountsEarned() > 0)
                {
                    data.add(constructionProject.getEmployeeArrayList().get(i).getEmpWeekInfoArrayList().get(y));
                    empNameCol.setCellValueFactory(new PropertyValueFactory<Employee.empWeekInfo, String>("empName"));
                    weekNumCol.setCellValueFactory(new PropertyValueFactory<Employee.empWeekInfo, Integer>("weekNum"));
                    amountCol.setCellValueFactory(new PropertyValueFactory<Employee.empWeekInfo, Double>("amountsEarned"));
                    startWeekCol.setCellValueFactory(new PropertyValueFactory<Employee.empWeekInfo, String>("startDate"));
                    endWeekCol.setCellValueFactory(new PropertyValueFactory<Employee.empWeekInfo, String>("endDate"));

                }
            }
        }
        viewEmpTableView.setItems(data);

        viewEmpScrollPane.setTranslateX(10);
        viewEmpScrollPane.setTranslateY(10);

        addEmployeeButton.setMinWidth(150);
        backFromViewEmpButton.setMinWidth(150);

        vBoxButtonsViewEmp.getChildren().addAll( addEmployeeButton, backFromViewEmpButton);
        vBoxButtonsViewEmp.setSpacing(10);
        vBoxButtonsViewEmp.setPadding(insets1);
        centerGridPane.add(vBoxButtonsViewEmp, 1,0);

    }

    public void removeViewEmpWindow()
    {
        vBoxButtonsViewEmp.getChildren().clear();

        if(centerGridPane.getChildren().contains(vBoxButtonsViewEmp))
            centerGridPane.getChildren().remove(vBoxButtonsViewEmp);

        viewEmpTableView.getItems().clear();
        viewEmpTableView.getColumns().clear();
        viewEmpScrollPane.setContent(null);

        if(centerGridPane.getChildren().contains(viewEmpScrollPane))
            centerGridPane.getChildren().remove(viewEmpScrollPane);

    }



    /*****************************************************************************************
     *                                                                                        *
     *                                  FUNCTIONS                                             *
     *                                                                                        *
     ******************************************************************************************/

    public void processNewWeek()
    {
        textArrayList.clear();
        fieldArrayList.clear();
        removeChart();
        removeWeekInfoScrollPane();
        removeButtonsVbox();

        expenseHeaderText.setTranslateX(10);
        expenseHeaderText.setTranslateY(10);
        expenseHeaderText.setTextAlignment(TextAlignment.CENTER);
        centerGridPane.add(expenseHeaderText,0,0);
        for (int i = 0; i < constructionProject.getEmployeeArrayList().size(); i++)
        {
            textArrayList.add(new Text(constructionProject.getEmployeeArrayList().get(i).getEmployeeName()));
            fieldArrayList.add(new TextField());
            fieldArrayList.get(i).setMaxWidth(60);
            fieldArrayList.get(i).setText("0");
            fieldArrayList.get(i).setMinWidth(60);
            fieldArrayList.get(i).setTranslateX(20);

            processWeekGridPane.add(textArrayList.get(i),0,i);
            processWeekGridPane.add(fieldArrayList.get(i),1,i);

        }

        expenseField.setText("0");
        expenseField.setMaxWidth(90);
        expenseField.setMinWidth(90);
        expenseText.setTranslateX(40);
        expenseField.setTranslateX(50);
        startPeriodField.setMaxWidth(90);
        startPeriodField.setMinWidth(90);
        startPeriodText.setTranslateX(40);
        startPeriodField.setTranslateX(50);
        endPeriodField.setMaxWidth(90);
        endPeriodField.setMinWidth(90);
        endPeriodText.setTranslateX(40);
        startPeriodField.setText(constructionProject.getMostRecentWeek() == "00/00/0000" ? constructionProject.getProjectStartDate() : constructionProject.getMostRecentWeek());
        endPeriodField.setTranslateX(50);
        String daySubString, monthSubString, yearSubString;
        int day, month, year;

        if(constructionProject.getMostRecentWeek() == "00/00/0000")
        {
            monthSubString = constructionProject.getProjectStartDate().substring(0, 2);
            daySubString = constructionProject.getProjectStartDate().substring(3, 5);
            yearSubString = constructionProject.getProjectStartDate().substring(6);
            day = Integer.valueOf(daySubString); month = Integer.valueOf(monthSubString); year = Integer.valueOf(yearSubString);
        }
        else
        {
            monthSubString = constructionProject.getMostRecentWeek().substring(0, 2);
            daySubString = constructionProject.getMostRecentWeek().substring(3, 5);
            yearSubString = constructionProject.getMostRecentWeek().substring(6);
            day = Integer.valueOf(daySubString); month = Integer.valueOf(monthSubString); year = Integer.valueOf(yearSubString);
        }
        day += 7;
        if (day > 31)
        {
            if (month + 1 <= 12)
                month++;
            else {
                month = 1;
                year++;
            }
            day = day - 31;
        }
        String endString = (month >= 10 ? Integer.toString(month) : "0" + Integer.toString(month)) + "/"
                + (day >= 10 ? Integer.toString(day) : "0" + Integer.toString(day)) + "/"
                + Integer.toString(year);

        endPeriodField.setText(endString);
        processWeekGridPane.setPadding(insets1);
        processWeekGridPane.add(expenseText,2,0);
        processWeekGridPane.add(expenseField, 3,0);
        processWeekGridPane.add(startPeriodText,2,1);
        processWeekGridPane.add(startPeriodField, 3,1);
        processWeekGridPane.add(endPeriodText,2,2);
        processWeekGridPane.add(endPeriodField,3,2);

        continueButtonFromPW.setTranslateY(30);

        backFromProcessWeekButton.setTranslateY(30);

        centerGridPane.add(continueButtonFromPW,0 ,2);
        centerGridPane.add(backFromProcessWeekButton, 0,2);
        continueButtonFromPW.setTranslateX(370);
        continueButtonFromPW.setTranslateY(15);
        backFromProcessWeekButton.setTranslateX(300);
        backFromProcessWeekButton.setTranslateY(15);

        processWeekScrollPane.setContent(processWeekGridPane);
        processWeekScrollPane.setPadding(insets1);
        processWeekScrollPane.setTranslateX(10);
        processWeekScrollPane.setTranslateY(10);
        processWeekScrollPane.setMinWidth(450);
        processWeekScrollPane.setMinHeight(500);
        processWeekScrollPane.setMaxHeight(500);
        centerGridPane.add(processWeekScrollPane,0,1);

    }

    public void pressedContinueFromProcessNewWindow()
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        for(int i = 0; i < constructionProject.getEmployeeArrayList().size(); i++)
        {
            if(fieldArrayList.get(i).getText().trim().isEmpty())
            {
                alert.setHeaderText("You can not leave empty spaces for employees earnings");
                alert.showAndWait();
                return;
            }
            if(!fieldArrayList.get(i).getText().matches("-?\\d+(\\.\\d+)?"))
            {
                alert.setHeaderText("You must enter numerical values for employees earnings");
                alert.showAndWait();
                return;
            }
        }
        if(expenseField.getText().trim().isEmpty())
        {
            alert.setHeaderText("You can not leave material expense empty");
            alert.showAndWait();
            return;
        }
        if(!expenseField.getText().matches("-?\\d+(\\.\\d+)?"))
        {
            alert.setHeaderText("You must enter numerical values for material expense");
            alert.showAndWait();
            return;
        }
        if(startPeriodField.getText().trim().isEmpty())
        {
            alert.setHeaderText("You can not leave Start date empty");
            alert.showAndWait();
            return;
        }
        if(endPeriodField.getText().trim().isEmpty())
        {
            alert.setHeaderText("You can not leave End date empty");
            alert.showAndWait();
            return;
        }

        if(! (startPeriodField.getCharacters().charAt(2) == '/' && startPeriodField.getCharacters().charAt(5) == '/' && startPeriodField.getText().length() == 10))
        {
            alert.setHeaderText("Please fix your start date.\n use this format: 00/00/0000. Ex 01/01/2017");
            alert.showAndWait();
            return;
        }
        if(! (endPeriodField.getCharacters().charAt(2) == '/' && endPeriodField.getCharacters().charAt(5) == '/' && endPeriodField.getText().length() == 10))
        {
            alert.setHeaderText("Please fix your end date.\n use this format: 00/00/0000. Ex 01/01/2017");
            alert.showAndWait();
            return;
        }

        int mostRecentWeekNum = 0;
        for(int i = 0; i < constructionProject.getEmployeeArrayList().size(); i++)
        {
            for(int y = 0; y < constructionProject.getEmployeeArrayList().get(i).getEmpWeekInfoArrayList().size(); y++)
            {
                String x = constructionProject.getEmployeeArrayList().get(i).getEmpWeekInfoArrayList().get(y).getEndDate(), q = constructionProject.getMostRecentWeek();
                if(x.equals(q))
                {
                    mostRecentWeekNum = constructionProject.getEmployeeArrayList().get(i).getEmpWeekInfoArrayList().get(y).getWeekNum();
                    y = constructionProject.getEmployeeArrayList().get(i).getEmpWeekInfoArrayList().size();
                    i = constructionProject.getEmployeeArrayList().size();
                    break;
                }
            }
        }


        double empExpense = 0.0, materialExpense;
        for(int i = 0; i < constructionProject.getEmployeeArrayList().size(); i++)
        {
            constructionProject.getEmployeeArrayList().get(i).addToEmpWeekInfoArrayList(Double.parseDouble(fieldArrayList.get(i).getText()),
                    startPeriodField.getText(), endPeriodField.getText(), mostRecentWeekNum+1);
            empExpense += Double.parseDouble(fieldArrayList.get(i).getText());
        }
        materialExpense = Double.parseDouble(expenseField.getText());

        String daySubString, monthSubString, yearSubString, endString, startString;
        int sday, smonth, syear, day, month, year;

        monthSubString = startPeriodField.getText().substring(0, 2);
        daySubString = startPeriodField.getText().substring(3, 5);
        yearSubString = startPeriodField.getText().substring(6);
        sday = Integer.valueOf(daySubString);
        smonth = Integer.valueOf(monthSubString);
        syear = Integer.valueOf(yearSubString);

        startString = (smonth >=10 ? String.valueOf(smonth) : "0" + String.valueOf(smonth))  + "/" +
                (sday >= 10  ? String.valueOf(sday)   : "0" + String.valueOf(sday))    + "/" + String.valueOf(syear);

        monthSubString = endPeriodField.getText().substring(0, 2);
        daySubString = endPeriodField.getText().substring(3, 5);
        yearSubString = endPeriodField.getText().substring(6);
        day = Integer.valueOf(daySubString);
        month = Integer.valueOf(monthSubString);
        year = Integer.valueOf(yearSubString);

        endString = (month >=10 ? String.valueOf(month) : "0" + String.valueOf(month))  + "/" +
                (day >= 10  ? String.valueOf(day)   : "0" + String.valueOf(day))    + "/" + String.valueOf(year);

        if(constructionProject.getWeekInfoArrayList().size() > 0)
            constructionProject.addWeek(startString, endString, empExpense, materialExpense, constructionProject.getWeekInfoArrayList().get(constructionProject.getWeekInfoArrayList().size()-1).getWeekNum()+1);
        else
            constructionProject.addWeek(startString, endString, empExpense, materialExpense, 1);

        constructionProject.setMostRecentWeek(endString);

        removeProcessWeek();
        addWeekTextScrollPane();
        updateWeekInfoScrollPane();

        if(weekTextsArrayList.size() > 0)
            weekTextsArrayList.clear();
    }

    public void resetOpenPJUI_2()
    {
        borderPaneOPJ2.getChildren().clear();
        centerGridPane.getChildren().clear();
        vBoxHeader.getChildren().clear();
        hBoxTopBase.getChildren().clear();
        buttonsVBox.getChildren().clear();
        processWeekGridPane.getChildren().clear();
        weekInfoScrollPane.setContent(null);
        vBoxWeekTextInfo.getChildren().clear();
        weekInfoTableView.getColumns().clear();
        
        data.clear();
        headers.clear();
        super.resetBaseUI();

    }

    /*****************************************************************************************
     *                                                                                        *
     *                                  READ/WRITE FUNCTIONS                                  *
     *                                                                                        *
     ******************************************************************************************/

    public void writeEmpEarnInfo(ConstructionProject constructionProject)
    {
        try
        {
            String path = System.getProperty("user.home") + "/ConstructionProject/ProgramFiles/Projects/" + constructionProject.getProjectName() + "Emps";
            for(int i = 0; i < constructionProject.getEmployeeArrayList().size(); i++)
            {
                PrintWriter writer = new PrintWriter(path + "//"
                        + constructionProject.getEmployeeArrayList().get(i).getEmployeeName() + ".txt");

                for(int y = 0; y < constructionProject.getEmployeeArrayList().get(i).getEmpWeekInfoArrayList().size(); y++)
                {
                    writer.print(Double.toString(constructionProject.getEmployeeArrayList().get(i).getEmpWeekInfoArrayList().get(y).getAmountsEarned()) + '|' );
                    writer.print(constructionProject.getEmployeeArrayList().get(i).getEmpWeekInfoArrayList().get(y).getStartDate() + '|');
                    writer.print(constructionProject.getEmployeeArrayList().get(i).getEmpWeekInfoArrayList().get(y).getEndDate() + '|');
                    writer.print(Integer.toString(constructionProject.getEmployeeArrayList().get(i).getEmpWeekInfoArrayList().get(y).getWeekNum()));
                    writer.println();
                }
                writer.close();
            }

        }catch (IOException e)
        {}
    }

    public void readEmpEarnInfo(ConstructionProject constructionProject)
    {
        int elementCounter = 0, pipePosition = 0, lastPipePosition = 0, weekNum = 0;
        String currentLineString, startDate = "", endDate = "";
        double amountEarned = 0.0;

        String dirPath = System.getProperty("user.home") + "/ConstructionProject/ProgramFiles/Projects/" + constructionProject.getProjectName() + "Emps";
        File file;
        Scanner input;

        try
        {
            for (int i = 0; i < constructionProject.getEmployeeArrayList().size(); i++)
            {
                String path = dirPath + "//" + constructionProject.getEmployeeArrayList().get(i).getEmployeeName() + ".txt";
                file = new File(path);
                input = new Scanner(file);
                while (input.hasNext())
                {
                    currentLineString = input.nextLine();
                    while (pipePosition != -1)
                    {
                        pipePosition = currentLineString.indexOf('|', lastPipePosition + 1);
                        if (elementCounter == 0)
                        {
                            amountEarned = Double.valueOf(currentLineString.substring(0, pipePosition));
                        }
                        else if (elementCounter == 1)
                        {
                            startDate = currentLineString.substring(lastPipePosition + 1, pipePosition);
                        }
                        else if (elementCounter == 2)
                        {
                            endDate = currentLineString.substring(lastPipePosition + 1, pipePosition);
                        }
                        else if (elementCounter == 3)
                        {
                            weekNum = Integer.valueOf(currentLineString.substring(lastPipePosition + 1));
                        }

                        elementCounter++;
                        lastPipePosition = pipePosition;
                    }

                    constructionProject.getEmployeeArrayList().get(i).addToEmpWeekInfoArrayList(amountEarned, startDate, endDate, weekNum);
                    elementCounter = 0;
                    pipePosition = 0;
                    lastPipePosition = 0;
                }

            }

        }catch (IOException e)
        {

        }

    }

    public void writeWeekInfoToFile(ConstructionProject constructionProject)
    {
        String fileName = constructionProject.getProjectName() + " Week Info";

        try{
            File outputFile = new File( System.getProperty("user.home") + "//ConstructionProject/ProgramFiles/WeekInfo/" + fileName + ".txt");
            PrintWriter writer = new PrintWriter(outputFile);
            if(!outputFile.exists())
            {
                System.out.println("File Does Not Exist");
                System.exit(0);
            }

            for(int i = 0, y = 0; i < constructionProject.getWeekInfoArrayList().size(); i++, y++)
            {
                writer.println(constructionProject.getWeekInfoArrayList().get(i).getStartDate() + '|'
                        + constructionProject.getWeekInfoArrayList().get(i).getEndDate() + '|' + Double.toString(constructionProject.getWeekInfoArrayList().get(i).getEmpExpense()) + '|'
                        + Double.toString(constructionProject.getWeekInfoArrayList().get(i).getMaterialExpense()) + '|' + Integer.toString(constructionProject.getWeekInfoArrayList().get(i).getWeekNum()));
            }
        writer.close();
        }catch(IOException e)
        {

        }
    }


    public void readWeekInfo(ConstructionProject constructionProject)
    {
        int elementCounter = 0,  pipePositon = 0, lastPipePosition = 0, weekNum = 0;
        String currentLineString, startDate = "", endDate = "";
        double empExpense = 0, materialExpense = 0;

        try
        {
            File file = new File(System.getProperty("user.home") + "/ConstructionProject/ProgramFiles/WeekInfo/" + constructionProject.getProjectName() + " Week Info.txt");
            Scanner input = new Scanner(file);

            while(input.hasNext())
            {
                currentLineString = input.nextLine();
                while(pipePositon != -1)
                {
                    pipePositon = currentLineString.indexOf('|', lastPipePosition+1);

                    if(elementCounter == 0)    //first time around line
                    {
                        startDate = currentLineString.substring(0, pipePositon);
                    }
                    else if (elementCounter == 1)
                    {
                        endDate = currentLineString.substring(lastPipePosition + 1, pipePositon);
                    }
                    else if(elementCounter == 2)
                    {
                        empExpense = Double.valueOf(currentLineString.substring(lastPipePosition + 1, pipePositon));
                    }
                    else if (elementCounter == 3)
                    {
                        materialExpense = Double.valueOf(currentLineString.substring(lastPipePosition+1, pipePositon));
                    }
                    else if (pipePositon == -1)
                    {
                        weekNum = Integer.valueOf(currentLineString.substring(lastPipePosition+1));
                    }

                    elementCounter++;
                    lastPipePosition = pipePositon;
                }
                constructionProject.addWeek(startDate, endDate, empExpense, materialExpense, weekNum);
                elementCounter = 0;
                pipePositon = 0;
                lastPipePosition = 0;
            }
            constructionProject.setMostRecentWeek(endDate);

        }catch(IOException e)
        {

        }
    }

    /*****************************************************************************************
     *                                                                                        *
     *                                  GETTERS & SETTERS                                     *
     *                                                                                        *
     ******************************************************************************************/

    public Button getRemoveWeekButton() { return removeWeekButton;  }

    public Button getRemoveWeekButton2() { return removeWeekButton2;  }

    public Button getViewEmpInfoButton() {  return viewEmpInfoButton;    }

    public Button getSaveButton() { return saveButton; }

    public Button getBackFromViewEmpButton() { return backFromViewEmpButton; }

    public Button getBackFromProcessWeekButton() { return backFromProcessWeekButton; }

    public Button getProcessNewWeekButton() { return processNewWeekButton; }

    public Button getAddEmployeeButton() {
        return addEmployeeButton;
    }

    public Button getBackFromRemoveWeekButton() {
        return backFromRemoveWeekButton;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    public Button getContinueButtonFromPW() {
        return continueButtonFromPW;
    }

    public ComboBox getComboBoxWeekToErase() {
        return comboBoxWeekToErase;
    }

    public BorderPane getBorderPane()
    {
        return borderPaneOPJ2;
    }

    public ConstructionProject getConstructionProject() { return constructionProject; }


}
