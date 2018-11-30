package app.ccb.domain.dtos;

import app.ccb.domain.entities.Employee;
import app.ccb.config.common.mappings.IHaveCustomMappings;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.boot.context.properties.PropertyMapper;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeImportDTO implements IHaveCustomMappings {
    @SerializedName("full_name")
    @Expose
    @NotNull
    private String fullName;

    @Expose
    @NotNull
    private BigDecimal salary;

    @SerializedName("started_on")
    @Expose
    private String startedOn;

    @SerializedName("branch_name")
    @Expose
    @NotNull
    private String branchName;

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BigDecimal getSalary() {
        return this.salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getStartedOn() {
        return this.startedOn;
    }

    public void setStartedOn(String startedOn) {
        this.startedOn = startedOn;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Override
    public void configureMappings(ModelMapper mapper) {
        TypeMap<EmployeeImportDTO, Employee> employeeMap = mapper.createTypeMap(EmployeeImportDTO.class, Employee.class);
        Converter<String, LocalDate> localDateConverter = date -> LocalDate.parse(date.getSource());
        Converter<String, String> firstName = name -> name.getSource().split("\\s+")[0];
        Converter<String, String> lastName = name -> name.getSource().split("\\s+")[1];
        employeeMap.addMappings(m -> m.using(firstName).map(EmployeeImportDTO::getFullName, Employee::setFirstName));
        employeeMap.addMappings(m -> m.using(lastName).map(EmployeeImportDTO::getFullName, Employee::setLastName));
        employeeMap.addMappings(m -> m.using(localDateConverter).map(EmployeeImportDTO::getStartedOn, Employee::setStartedOn));
        employeeMap.addMappings(m -> m.skip(Employee::setBranch));
        employeeMap.addMappings(m -> m.skip(Employee::setClients));
    }
}


