import java.util.Objects;


public class CommissionEmployee extends Employee
{
    private  float grossSales;
    private int commission;

    public CommissionEmployee(int commission, float grossSales, String first, String last, int id) throws Exception
    {
        super(first, last, id);
        if(commission < 0)
            throw new IllegalArgumentException("commission error");
        this.commission = commission;
        if(grossSales < 0)
            throw new IllegalArgumentException("grossSales error");
        this.grossSales = grossSales;
    }
    public CommissionEmployee()
    {
        super();
        grossSales = 0;
        commission = 0;
    }

    public void setCommission(int commission)throws Exception {
        if(commission < 0)
            throw new IllegalArgumentException("commission error");
        this.commission = commission;
    }
    public void setGrossSales(float grossSales)throws Exception {
        if(grossSales < 0)
            throw new IllegalArgumentException("grossSales error");
        this.grossSales = grossSales;
    }

    public int getCommission() {
        return commission;
    }
    public float getGrossSales() {
        return grossSales;
    }

    @Override
    public String
    toString() {
        return "CommissionEmployee{" +
                super.toString() +
                "grossSales=" + grossSales +
                ", commission=" + commission +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommissionEmployee)) return false;
        if (!super.equals(o)) return false;
        CommissionEmployee that = (CommissionEmployee) o;
        return Float.compare(that.getGrossSales(), getGrossSales()) == 0 &&
                getCommission() == that.getCommission();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getGrossSales(), getCommission());
    }

    @Override
    public float earnings()
    {
        return ((float)commission/100) * grossSales;
    }
}
