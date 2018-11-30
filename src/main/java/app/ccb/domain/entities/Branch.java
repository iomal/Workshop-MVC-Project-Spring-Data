package app.ccb.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity(name="branch")
@Table(name="branches")
public class Branch extends BaseEntity{
    @NotNull
    private String name;

    @OneToMany (mappedBy = "branch")
    private Set<Employee> employee;

    public Branch() {
    }

    public Branch(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Set<Employee> getEmployee() {
        return this.employee;
    }

    public void setEmployee(Set<Employee> employee) {
        this.employee = employee;
    }
}
