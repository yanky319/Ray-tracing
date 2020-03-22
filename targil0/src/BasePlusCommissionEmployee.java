import java.util.Objects;

public class BasePlusCommissionEmployee extends CommissionEmployee
{
    private float baseSalary;

    public BasePlusCommissionEmployee(int commission, float grossSales, String first, String last, int id, float baseSalary) throws Exception
    {
        super(commission, grossSales, first, last, id);
        if(baseSalary < 0)
            throw new IllegalArgumentException("baseSalary error");
        this.baseSalary = baseSalary;
    }
    public BasePlusCommissionEmployee()
    {
        super();
        this.baseSalary = 0;
    }

    public void setBaseSalary(float baseSalary)throws Exception {
        if(baseSalary < 0)
            throw new IllegalArgumentException("baseSalary error");
        this.baseSalary = baseSalary;
    }

    public float getBaseSalary() {
        return baseSalary;
    }

    @Override
    public String toString() {
        return "BasePlusCommissionEmployee{" +
                super.toString() +
                "baseSalary=" + baseSalary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasePlusCommissionEmployee)) return false;
        if (!super.equals(o)) return false;
        BasePlusCommissionEmployee that = (BasePlusCommissionEmployee) o;
        return Float.compare(that.getBaseSalary(), getBaseSalary()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBaseSalary());
    }

    @Override
    public float earnings()
    {
        return super.earnings() + this.baseSalary;
    }

}
