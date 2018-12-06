package app.ccb.domain.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "bank-accounts")
@XmlAccessorType(XmlAccessType.FIELD)
public class BankAccountRootDTO {

    @XmlElement(name = "bank-account")
    private List<BankAccountImportDTO> bankAccountImportDTOs;

    public BankAccountRootDTO() {
    }

    public List<BankAccountImportDTO> getBankAccountImportDTOs() {
        return this.bankAccountImportDTOs;
    }

    public void setBankAccountImportDTOs(List<BankAccountImportDTO> bankAccountImportDTOs) {
        this.bankAccountImportDTOs = bankAccountImportDTOs;
    }
}
