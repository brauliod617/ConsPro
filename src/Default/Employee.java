package Default;

import java.util.ArrayList;

/**
 * Description:
 * Programmer : Braulio Duarte
 * Project/Lab: Programming Exercise
 * Last Modified: 12/17/16.
 */
public class Employee {
    private String employeeName;
    private double earnedToDate;
    private ArrayList<empWeekInfo> empWeekInfoArrayList = new ArrayList<>();

    public Employee()
    {

    }
    public Employee(String empname)
    {
        this.employeeName = empname;
    }

    public void addToEmpWeekInfoArrayList(Double amount, String startDate, String endDate, int weekNum)
    {
        empWeekInfoArrayList.add(new empWeekInfo(amount, startDate, endDate, weekNum, employeeName));
        earnedToDate += amount;
    }

    //TODO: Error here. When removed last week frm list generated indexoutofbounds exceptions
    public void removeFromEmpWeekInfoArrayList(int weekNum)
    {
        empWeekInfoArrayList.remove(weekNum);
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getEarnedToDate() {
        return earnedToDate;
    }

    public ArrayList<empWeekInfo> getEmpWeekInfoArrayList() {
        return empWeekInfoArrayList;
    }

    public class empWeekInfo
    {
        private Double amountsEarned;
        private String startDate;
        private String endDate;
        private int weekNum;
        private String empName;

        public empWeekInfo(double amountsEarned, String startDate, String endDate, int weekNum, String empName)
        {
            this.amountsEarned = amountsEarned;
            this.startDate = startDate;
            this.endDate = endDate;
            this.weekNum = weekNum;
            this.empName = empName;
        }

        public double getAmountsEarned() {
            return amountsEarned;
        }

        public void setAmountsEarned(double amountsEarned) {
            this.amountsEarned = amountsEarned;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public int getWeekNum() {
            return weekNum;
        }

        public String getEmpName() {
            return empName;
        }
    }
}
