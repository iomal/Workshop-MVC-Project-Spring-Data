package app.ccb.domain.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "bankAccount")
@Table(name = "bank_accounts")
public class BankAccount extends BaseEntity{

    private String accountNumber;

    private BigDecimal balance;

    @OneToOne
    private Client client;

    @OneToMany(mappedBy = "bankAccount")
    private Set<Card> cards;

    public BankAccount() {
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Card> getCards() {
        return this.cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }


}
