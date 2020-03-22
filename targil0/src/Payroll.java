import java.text.DecimalFormat;

/**
 * yaakov yitzchok taber
 * 319187324
 */

public class Payroll
{

    public static DecimalFormat df = new DecimalFormat("0.00"); //formats the float numbers to 2 decimal places

    public static void main(String[] args) {
        try {
            Employee[] employees = new Employee[3];
            employees[0] = new HourlyEmployee(180, (float) 2.4, "yanky", "taber", 319187324);
            employees[1] = new CommissionEmployee(32, (float) 167.3, "moshe", "helfgot", 206262404);
            employees[2] = new BasePlusCommissionEmployee(16, (float) 630.4, "lior", "jabra", 319187404, (float) ( 630.2));
            for (Employee i : employees) {
                if(i instanceof BasePlusCommissionEmployee)
                {
                    float earnings = (float)(i.earnings() + 0.1*i.earnings());
                    System.out.println(i + ", earnings= " + df.format(earnings));
                }
                else
                {
                    System.out.println(i + ", earnings= " + df.format(i.earnings()));
                }
            }
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }
    }
}
