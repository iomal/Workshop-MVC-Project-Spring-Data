package app.ccb.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name="Employee")
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private BigDecimal salary;

    private LocalDate startedOn;

    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @NotNull
    private Branch branch;

    @ManyToMany (mappedBy = "employees",fetch = FetchType.LAZY)
    private Set<Client> clients;

    public Employee() {
        this.clients = new HashSet<>();
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getStartedOn() {
        return this.startedOn;
    }

    public void setStartedOn(LocalDate startedOn) {
        this.startedOn = startedOn;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return "Employee id = " + id +"\n"+ firstName + " " + lastName + "\n"+
                "      salary: " + String.format("%.2f",salary) +"\n"+
                "      startedOn: " + startedOn + "\n"+ branch +
                "      clients number: " + clients.size()+"\n"+"\n";
    }
}
