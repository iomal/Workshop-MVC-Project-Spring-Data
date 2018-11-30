package app.ccb.domain.dtos;

import app.ccb.config.common.mappings.IHaveCustomMappings;
import app.ccb.domain.entities.Branch;
import app.ccb.domain.entities.Employee;
import com.google.gson.annotations.Expose;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;

public class BranchImportDTO implements IHaveCustomMappings {
    @Expose
    @NotNull
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void configureMappings(ModelMapper mapper) {
        mapper.createTypeMap(BranchImportDTO.class,Branch.class)
     .addMappings(m -> m.skip(Branch::setEmployee));
    }
}
