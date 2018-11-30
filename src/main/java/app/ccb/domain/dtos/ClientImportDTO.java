package app.ccb.domain.dtos;

import app.ccb.config.common.mappings.IHaveCustomMappings;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.modelmapper.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;


public class ClientImportDTO implements IHaveCustomMappings {

    @SerializedName("first_name")
    @Expose
    @NotNull
    private String firstName;

    @SerializedName("last_name")
    @Expose
    @NotNull
    private String lastname;
    @Expose
    private Integer age;

    @SerializedName("appointed_employee")
    @Expose
    private String employeeName;

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public void configureMappings(ModelMapper mapper) {
        TypeMap<ClientImportDTO, Client> employeeMap = mapper.createTypeMap(ClientImportDTO.class, Client.class);
        employeeMap.addMappings(m -> m.skip(Client::setBankAccount));
        employeeMap.addMappings(m -> m.skip(Client::setEmployees));
        Converter <ClientImportDTO, String> fullName = ctx -> this.generateFullName(ctx.getSource().getFirstName(),
                ctx.getSource().getLastname());
        employeeMap.addMappings(new PropertyMap<ClientImportDTO, Client>() {
            @Override
            protected void configure() {
                using(fullName).map(source,destination.getFullName());
            }
        });


    }
    private String generateFullName(String fristName, String lastName){
        return fristName+ " "+lastName;
    }
}
