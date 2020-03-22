import java.util.Objects;

public abstract class Employee
{
    private String firstName;
    private String lastName;
    private int id;

    public Employee(String first, String last, int id) throws Exception
    {
        firstName = first;
        lastName = last;
        if(id < 0)
            throw new IllegalArgumentException("id error");
        this.id = id;
    }

    public Employee()
    {
        firstName = "plony";
        lastName = "plony";
        this.id = 0;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setId(int id) {
        if(id < 0)
            throw new IllegalArgumentException("id error");
        this.id = id;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return getId() == employee.getId() &&
                Objects.equals(getFirstName(), employee.getFirstName()) &&
                Objects.equals(getLastName(), employee.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getId());
    }

    public abstract float earnings();
}
