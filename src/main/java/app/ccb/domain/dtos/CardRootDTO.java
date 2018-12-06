package app.ccb.domain.dtos;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name = "cards")
@XmlAccessorType (XmlAccessType.FIELD)
public class CardRootDTO {

    @XmlElement(name = "card")
    private List<CardImportDTO> cardImportDTOs;

    public CardRootDTO() {
    }

    public List<CardImportDTO> getClientImportDTOs() {
        return this.cardImportDTOs;
    }

    public void setClientImportDTOs(List<CardImportDTO> cardImportDTOs) {
        this.cardImportDTOs = cardImportDTOs;
    }
}
