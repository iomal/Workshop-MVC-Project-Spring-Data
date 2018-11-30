package app.ccb.domain.dtos;

import app.ccb.config.common.mappings.IHaveCustomMappings;
import app.ccb.config.common.mappings.MapperConfig;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Employee;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlRootElement(name = "bank-account")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankAccountImportDTO implements IHaveCustomMappings, Serializable {

    private final MapperConfig mapperConfig;

    @XmlElement(name = "account-number")
    private String accountNumber;

    @XmlAttribute(name = "client")
    private String clientName;

    @XmlElement(name = "balance")
    private BigDecimal accountBalance;


    public BankAccountImportDTO() {
        mapperConfig = null;
    }
    public BankAccountImportDTO(MapperConfig mapperConfig) {
        this.mapperConfig = mapperConfig;
    }

    public MapperConfig getMapperConfig() {
        return this.mapperConfig;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getClientName() {
        return this.clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public BigDecimal getAccountBalance() {
        return this.accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public void configureMappings(ModelMapper mapper) {
        TypeMap<BankAccountImportDTO, BankAccount> employeeMap = mapper.createTypeMap(BankAccountImportDTO.class,
                BankAccount.class);
        employeeMap.addMappings(m -> m.skip(BankAccount::setCards));
        employeeMap.addMappings(m -> m.skip(BankAccount::setClient));
    }


}
