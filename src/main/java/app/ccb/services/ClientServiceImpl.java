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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
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
            Client clientnew=this.clientRepository.findById(1L).orElse(null);
            if(clientnew!=null)
            System.out.println(clientnew.hashCode());
            System.out.println(clientnew);
            Client clientnew2= this.clientRepository.findById(1L).orElse(null);
            if(clientnew2!=null)
            System.out.println(clientnew2.hashCode());
            System.out.println(clientnew2);
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
            Client clientExist =this.clientRepository.findByFullName(client.getFullName()).orElse(null);
            if (clientExist!=null) {
                clientExist.getEmployees().add(employee);
            } else {
                this.clientRepository.save(client);
            }
            importResult.append(String.format("Successfully imported %s – %s.",
                    client.getClass().getSimpleName(), client.getFullName()));
            importResult.append(System.lineSeparator());
        }
        return importResult.toString().trim();
    }

    @Override
    public String exportFamilyGuy() {
        return this.clientRepository.familyGuy(new PageRequest(0,1)).get(0).toString();
    }

}
