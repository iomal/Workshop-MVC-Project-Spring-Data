package app.ccb.services;

import app.ccb.config.common.constants.Constants;
import app.ccb.config.common.parsers.JaxbParser;
import app.ccb.config.common.util.FileUtil;
import app.ccb.config.common.util.ValidationUtil;
import app.ccb.domain.dtos.BankAccountRootDTO;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private final FileUtil fileUtil;
    private final ValidationUtil validator;
    private final ModelMapper mapper;
    private final JaxbParser jaxbParser;
    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public BankAccountServiceImpl(FileUtil fileUtil, ValidationUtil validator,
                                  ModelMapper mapper, JaxbParser jaxbParser,
                                  BankAccountRepository bankAccountRepository,
                                  ClientRepository clientRepository) {
        this.fileUtil = fileUtil;
        this.validator = validator;
        this.mapper = mapper;
        this.jaxbParser = jaxbParser;
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public Boolean bankAccountsAreImported() {
        return this.bankAccountRepository.count() != 0;
    }

    @Override
    public String readBankAccountsXmlFile() throws IOException {
        return fileUtil.readFile(Constants.BANK_ACCOUNT_XML_RELATIVE_PATH);
    }

    @Override
    public String importBankAccounts() throws IOException, JAXBException {
        BankAccountRootDTO bankAccountRootDTOs = jaxbParser.toString(Constants.BANK_ACCOUNT_XML_FULL_PATH,
                BankAccountRootDTO.class);
        StringBuilder importResult = new StringBuilder();

        bankAccountRootDTOs.getBankAccountImportDTOs().forEach(bankAccountImportDTO -> {

            if (!validator.isValid(bankAccountImportDTO)) {
                importResult.append("Error: Incorrect Data!");
                importResult.append(System.lineSeparator());
            } else {
                BankAccount bankAccount = mapper.map(bankAccountImportDTO, BankAccount.class);
                Client client = this.clientRepository
                        .findByFullName(bankAccountImportDTO.getClientName()).orElse(null);
                if (client == null) {
                    importResult.append("Error: Incorrect Data!");
                    importResult.append(System.lineSeparator());
                }
                else
                {
                    bankAccount.setClient(client);
                    this.bankAccountRepository.saveAndFlush(bankAccount);
                    importResult.append(String.format("Successfully imported %s – %s.",
                            bankAccount.getClass().getSimpleName(), bankAccount.getAccountNumber()));
                    importResult.append(System.lineSeparator());
                }

            }
        });
        return importResult.toString().trim();
    }
}
