package app.ccb.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Client")
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String fullName;
    private Integer age;

    @OneToOne(mappedBy = "client")
    private BankAccount bankAccount;

    @ManyToMany
//    @JoinTable(name = "clients_employees",
//            joinColumns = @JoinColumn(name = "client_id"),
//            inverseJoinColumns = @JoinColumn(name = "employee_id")
//    )
    private Set<Employee> employees;

    public Client() {
        employees = new HashSet<>();
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BankAccount getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.getClients().add(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.getClients().remove(this);
    }

//    @Override
//    public String toString() {
//        return "Client id=" + id +"\n"+ fullName + '\n' +
//                "      age: " + age +"\n"+
//                "      bank account: " + bankAccount+"\n";
//    }
}
