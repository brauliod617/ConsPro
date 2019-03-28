package Default;

import javafx.scene.control.Alert;

import java.util.ArrayList;

/**
 * Description:
 * Programmer : Braulio Duarte
 * Project/Lab: Programming Exercise
 * Last Modified: 12/17/16.
 */

public class ConstructionProject {
    private int ProjectNum;
    private String projectAddress;
    private String projectName;
    private String projectStartDate;
    private double GrossPayForProject;
    private double currentProfit;
    private double totalMaterialExpense;
    private double totalEmployeeExpense;
    private String mostRecentWeek;
    private int mostRecentWeekNumber;
    private Integer weeksAdded;
    private ArrayList<Employee> employeeArrayList = new ArrayList<Employee>();
    private ArrayList<WeekInfo> weekInfoArrayList = new ArrayList<>();


    public ConstructionProject(int numOfEmployees)
    {
        for(int i = 0; i < numOfEmployees; i++)

        {
            employeeArrayList.add(new Employee());
        }
        currentProfit = 0;
        totalMaterialExpense = 0;
        totalEmployeeExpense = 0;
        mostRecentWeek = "00/00/0000";
        weeksAdded = 0;
    }

    public void removeWeekInfo(int weeknum)
    {
        int index = -1;

        for(int i = 0; i < weekInfoArrayList.size(); i++)
        {
            if(weekInfoArrayList.get(i).getWeekNum() == weeknum)
            {
                index = i;
                i = weekInfoArrayList.size();
            }
        }
        if(index == -1)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error has occurred");
            alert.showAndWait();
            System.exit(0);
        }


        totalMaterialExpense -= weekInfoArrayList.get(index).getMaterialExpense();
        totalEmployeeExpense -= weekInfoArrayList.get(index).getEmpExpense();
        currentProfit += weekInfoArrayList.get(index).getEmpExpense();
        currentProfit += weekInfoArrayList.get(index).getMaterialExpense();
        weekInfoArrayList.remove(index);
        weeksAdded--;

        for (int i = 0; i < employeeArrayList.size(); i++)
        {
            for(int y = 0; y < employeeArrayList.get(i).getEmpWeekInfoArrayList().size(); y++)
            {
                if(employeeArrayList.get(i).getEmpWeekInfoArrayList().get(y).getWeekNum() == weeknum)
                    employeeArrayList.get(i).removeFromEmpWeekInfoArrayList(y);
            }

        }
        if(weekInfoArrayList.size() > 1)
            mostRecentWeek = weekInfoArrayList.get(weekInfoArrayList.size()-1).endDate;
        else
            mostRecentWeek = projectStartDate;
    }

    public void addWeek(String startDate, String endDate, double empExpense, double materialExpense, int weekNum)
    {
        weekInfoArrayList.add(new WeekInfo());
        weekInfoArrayList.get(weeksAdded).setStartDate(startDate);
        weekInfoArrayList.get(weeksAdded).setEndDate(endDate);
        weekInfoArrayList.get(weeksAdded).setEmpExpense(empExpense);
        weekInfoArrayList.get(weeksAdded).setMaterialExpense(materialExpense);
        weekInfoArrayList.get(weeksAdded).setWeekNum(weekNum);
        weeksAdded++;
        currentProfit -= empExpense + materialExpense;
        totalEmployeeExpense += empExpense;
        totalMaterialExpense += materialExpense;
    }

    public ArrayList<Employee> getEmployeeArrayList() {
        return employeeArrayList;
    }

    public ArrayList<WeekInfo> getWeekInfoArrayList() {
        return weekInfoArrayList;
    }

    public String getMostRecentWeek() { return mostRecentWeek; }

    public void setMostRecentWeek(String mostRecentWeek) {
        if(mostRecentWeek == "")
            this.mostRecentWeek = "00/00/0000";
        else
            this.mostRecentWeek = mostRecentWeek;
    }

    public int getProjectNum() {
        return ProjectNum;
    }

    public void setProjectNum(int projectNum) {
        ProjectNum = projectNum;
    }

    public double getGrossPayForProject() {
        return GrossPayForProject;
    }

    public void setGrossPayForProject(double grossPayForProject) {
        GrossPayForProject = grossPayForProject;
        currentProfit = grossPayForProject;
    }

    public String getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
        this.mostRecentWeek = projectStartDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }


    public double getCurrentProfit() {
        return currentProfit;
    }

    public double getTotalMaterialExpense() {
        return totalMaterialExpense;
    }

    public double getTotalEmployeeExpense() {
        return totalEmployeeExpense;
    }

    public int getWeeksAdded() {
        return weeksAdded;
    }

    public class WeekInfo{
        private String startDate = "00/00/0000";
        private String endDate = "00/00/0000";
        private double empExpense = 0;
        private double materialExpense = 0;
        private int weekNum = 0;

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public int getWeekNum() {
            return weekNum;
        }

        public void setWeekNum(int weekNum) {
            this.weekNum = weekNum;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public double getEmpExpense() {
            return empExpense;
        }

        public void setEmpExpense(double empExpense) {
            this.empExpense = empExpense;
        }

        public double getMaterialExpense() {
            return materialExpense;
        }

        public void setMaterialExpense(double materialExpense) {
            this.materialExpense = materialExpense;
        }
    }

}
