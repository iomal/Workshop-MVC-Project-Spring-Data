package app.ccb.domain.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "bank-accounts")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankAccountDTO {

    @XmlElement(name = "bank-account")
    private List<BankAccountImportDTO> bankAccountImportDTOs;

    public BankAccountDTO() {
    }

    public List<BankAccountImportDTO> getBankAccountImportDTOs() {
        return this.bankAccountImportDTOs;
    }

    public void setBankAccountImportDTOs(List<BankAccountImportDTO> bankAccountImportDTOs) {
        this.bankAccountImportDTOs = bankAccountImportDTOs;
    }
}
