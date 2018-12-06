package app.ccb.domain.entities;

import app.ccb.domain.Enums.CardStatus;

import javax.persistence.*;

@Entity(name = "card")
@Table(name = "cards")
public class Card extends BaseEntity {

    private String cardNumber;

    @Enumerated (EnumType.STRING)
    @Column (name="card_status")
    private CardStatus cardStatus;

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

    public CardStatus getCardStatus() { return this.cardStatus; }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public BankAccount getBankAccount() {
        return this.bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public String toString() {
        return  "      card number: " + cardNumber + '\n' +
                "      card status: " + cardStatus + "\n";
    }
}

