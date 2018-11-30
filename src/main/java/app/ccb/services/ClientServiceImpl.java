package app.ccb.services;

import app.ccb.config.common.constants.Constants;
import app.ccb.config.common.parsers.GsonParser;
import app.ccb.config.common.util.FileUtil;
import app.ccb.config.common.util.ValidationUtil;
import app.ccb.domain.dtos.ClientImportDTO;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validator;
    private final GsonParser gsonParser;
    private final ModelMapper mapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, EmployeeRepository employeeRepository,
                             FileUtil fileUtil, ValidationUtil validator, GsonParser gsonParser, ModelMapper mapper) {
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.validator = validator;
        this.gsonParser = gsonParser;
        this.mapper = mapper;
    }


    @Override
    public Boolean clientsAreImported() {

        return this.clientRepository.count() != 0;
    }

    @Override
    public String readClientsJsonFile() throws IOException {

        return fileUtil.readFile(Constants.CLIENTS_JSON_PATH);
    }

    @Override
    public String importClients(String clients) throws IOException {
        StringBuilder importResult = new StringBuilder();
        ClientImportDTO[] clientImportDTO = gsonParser.fromString(this.readClientsJsonFile(), ClientImportDTO[].class);
        for (ClientImportDTO importDTO : clientImportDTO) {
            if (!validator.isValid(importDTO)) {
                importResult.append("Error: Incorrect Data!");
                continue;
            }
            Client client = mapper.map(importDTO, Client.class);
            String firstName = importDTO.getEmployeeName().split("\\s+")[0];
            String lastName = importDTO.getEmployeeName().split("\\s+")[1];
            Employee employee = this.employeeRepository.findByFirstNameAndLastName(firstName, lastName)
                    .orElse(null);
            if (employee == null) {
                importResult.append("Error: Incorrect Data!");
                importResult.append(System.lineSeparator());
                continue;
            }
            if (this.clientRepository.findByFullName(client.getFullName()).isPresent()) {
                this.clientRepository.findByFullName(client.getFullName()).get().addEmployee(employee);
            } else {
                this.clientRepository.saveAndFlush(client);
            }
            importResult.append(String.format("Successfully imported %s – %s.",
                    client.getClass().getSimpleName(), client.getFullName()));
            importResult.append(System.lineSeparator());
        }
        return importResult.toString().trim();
    }

    @Override
    public String exportFamilyGuy() {
        // TODO : Implement Me
        return null;
    }

}
