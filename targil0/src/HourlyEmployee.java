import java.util.Objects;


public class HourlyEmployee extends Employee
{
    private int hours;
    private float wage;

    public HourlyEmployee(int hours, float wage, String first, String last, int id) throws Exception
    {
        super(first, last, id);
        if(hours < 0)
            throw new IllegalArgumentException("hours error");
        this.hours = hours;
        if(wage < 0)
            throw new IllegalArgumentException("wage error");
        this.wage = wage;
    }
    public HourlyEmployee()
    {
        super();
        hours = 0;
        wage = 0;
    }

    public void setHours(int hours)throws Exception {
        if(hours < 0)
            throw new IllegalArgumentException("hours error");
        this.hours = hours;
    }
    public void setWage(float wage)throws Exception {
        if(wage < 0)
            throw new IllegalArgumentException("wage error");
        this.wage = wage;
    }

    public int getHours() {
        return hours;
    }
    public float getWage() {
        return wage;
    }

    @Override
    public String toString() {
        return "HourlyEmployee{" +
               super.toString() +
                "hours=" + hours +
                ", wage=" + wage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HourlyEmployee)) return false;
        if (!super.equals(o)) return false;
        HourlyEmployee that = (HourlyEmployee) o;
        return getHours() == that.getHours() &&
                Float.compare(that.getWage(), getWage()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getHours(), getWage());
    }

    @Override
    public float earnings() {
        return hours * wage;
    }
}
