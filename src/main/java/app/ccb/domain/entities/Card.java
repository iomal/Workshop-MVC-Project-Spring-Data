package app.ccb.domain.entities;

import javax.persistence.*;

@Entity(name = "card")
@Table(name = "cards")
public class Card extends BaseEntity {

    private String cardNumber;

    @Enumerated (EnumType.STRING)
    @Column (length=7)
    private Enum cardStatus;

    @ManyToOne
    private BankAccount bankAccount;

    public Card() {
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Enum getCardStatus() { return this.cardStatus; }

    public void setCardStatus(Enum cardStatus) {
        this.cardStatus = cardStatus;
    }

    public BankAccount getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
    }

