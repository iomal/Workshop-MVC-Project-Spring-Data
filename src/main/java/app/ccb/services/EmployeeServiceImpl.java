package app.ccb.services;


import app.ccb.config.common.constants.Constants;
import app.ccb.config.common.parsers.GsonParser;
import app.ccb.domain.dtos.EmployeeImportDTO;
import app.ccb.domain.entities.Branch;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.BranchRepository;
import app.ccb.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.ccb.config.common.util.FileUtil;
import app.ccb.config.common.util.ValidationUtil;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final FileUtil fileUtil;
    private final ValidationUtil validator;
    private final GsonParser gsonParser;
    private final ModelMapper mapper;
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;

    @Autowired
    public EmployeeServiceImpl(FileUtil fileUtil, ValidationUtil validator,
                               GsonParser gsonParser, ModelMapper mapper,
                               EmployeeRepository employeeRepository,
                               BranchRepository branchRepository) {
        this.fileUtil = fileUtil;
        this.validator = validator;
        this.gsonParser = gsonParser;
        this.mapper = mapper;
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    public Boolean employeesAreImported() {

        return this.employeeRepository.count() != 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return fileUtil.readFile(Constants.EMPLOYEES_JSON_PATH);

    }

    @Override
    public String importEmployees(String employees) throws IOException {
        EmployeeImportDTO[] employeeImportDTOS = gsonParser.fromString(this.readEmployeesJsonFile(),
                EmployeeImportDTO[].class);
        StringBuilder importResult = new StringBuilder();
        for (EmployeeImportDTO employeeImportDTO : employeeImportDTOS) {
            Employee employee = new Employee();
                    mapper.map(employeeImportDTO, employee);
            if (this.employeeRepository
                    .findByFirstNameAndLastName(employee.getFirstName(), employee.getLastName()).isPresent()) {
                importResult.append("Error: Incorrect Data!");
                importResult.append(System.lineSeparator());
                continue;
            }
            Branch branch=this.branchRepository.findByName(employeeImportDTO.getBranchName()).orElse(null);
            if(branch==null)
            {this.branchRepository.saveAndFlush(new Branch(employeeImportDTO.getBranchName()));}
            employee.setBranch(branch);
            this.employeeRepository.saveAndFlush(employee);
            importResult.append(String.format("Successfully imported %s – %s %s.",
                    employee.getClass().getSimpleName(), employee.getFirstName(), employee.getLastName()));
            importResult.append(System.lineSeparator());
                    }
        String result = importResult.toString().trim();
        System.out.println(result);
        return result;
    }

    @Override
    public String exportTopEmployees() {
        // TODO : Implement Me
        return null;
    }
}
