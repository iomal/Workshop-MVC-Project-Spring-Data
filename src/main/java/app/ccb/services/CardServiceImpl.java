package app.ccb.services;

import app.ccb.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Boolean cardsAreImported() {
     return this.cardRepository.count() != 0;
    }

    @Override
    public String readCardsXmlFile() {
        // TODO : Implement Me
        return null;
    }

    @Override
    public String importCards() {
        // TODO : Implement Me
        return null;
    }
}
