package app.ccb.domain.dtos;

import app.ccb.config.common.mappings.IHaveCustomMappings;
import app.ccb.domain.Enums.CardStatus;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.domain.entities.Client;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "card")
@XmlAccessorType(XmlAccessType.FIELD)
public class CardImportDTO implements IHaveCustomMappings {

    @XmlElement(name = "card-number")
    private String cardNumber;

    @XmlAttribute(name = "account-number")
    private String bankAccount;

    @XmlAttribute(name = "status")
    private String cardStatus;

    public CardImportDTO() {
    }

    @NotNull
    public String getCardNumber() {
        return this.cardNumber;
    }

    public String getBankAccount() {
        return this.bankAccount;
    }

    @NotNull
    public String getCardStatus() {
        return this.cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public void configureMappings(ModelMapper mapper) {
        TypeMap<CardImportDTO, Card> cardMap = mapper.createTypeMap(CardImportDTO.class, Card.class);
        Converter<String, CardStatus> cardStatusConverter = ctx -> CardStatus.valueOf(CardStatus.class, ctx.getSource());
        cardMap.addMappings(m -> m.using(cardStatusConverter).map(CardImportDTO::getCardStatus, Card::setCardStatus))
                .addMappings(m -> m.skip(Card::setBankAccount));
    }
}
