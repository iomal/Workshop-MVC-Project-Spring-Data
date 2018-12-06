package app.ccb.services;

import app.ccb.config.common.constants.Constants;
import app.ccb.config.common.parsers.JaxbParser;
import app.ccb.config.common.util.FileUtil;
import app.ccb.config.common.util.ValidationUtil;
import app.ccb.domain.dtos.CardImportDTO;
import app.ccb.domain.dtos.CardRootDTO;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.CardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class CardServiceImpl implements CardService {

    private final FileUtil fileUtil;
    private final ValidationUtil validator;
    private final ModelMapper mapper;
    private final JaxbParser jaxbParser;
    private final BankAccountRepository bankAccountRepository;
    private final CardRepository cardRepository;
    @Autowired
    public CardServiceImpl(FileUtil fileUtil, ValidationUtil validator, ModelMapper mapper, JaxbParser jaxbParser,
                           BankAccountRepository bankAccountRepository, CardRepository cardRepository) {
        this.fileUtil = fileUtil;
        this.validator = validator;
        this.mapper = mapper;
        this.jaxbParser = jaxbParser;
        this.bankAccountRepository = bankAccountRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public Boolean cardsAreImported() {
     return this.cardRepository.count() != 0;
    }

    @Override
    public String readCardsXmlFile() throws IOException, JAXBException {
        return fileUtil.readFile(Constants.CARDS_XML_RELATIVE_PATH);
    }

    @Override
    public String importCards() throws JAXBException {
        CardRootDTO cardImportDTOS= jaxbParser
                .toString(Constants.CARDS_XML_FULL_PATH, CardRootDTO.class);
        StringBuilder importResult = new StringBuilder();
        cardImportDTOS.getClientImportDTOs().forEach(cardImportDTO->
        {
            if (!validator.isValid(cardImportDTO)){
                importResult.append("Error: Incorrect Data!");
                return;
            }
            Card card =new Card();
            mapper.map(cardImportDTO,card);
            BankAccount bankAccount = this.bankAccountRepository.findByAccountNumber(cardImportDTO.getBankAccount())
                    .orElse(null);
            if (bankAccount==null){
                importResult.append("Error: Incorrect Data!");
                return;
            }
            card.setBankAccount(bankAccount);
            this.cardRepository.save(card);
        });
        return importResult.toString().trim();
    }
}
