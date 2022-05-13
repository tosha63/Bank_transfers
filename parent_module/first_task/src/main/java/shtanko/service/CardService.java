package shtanko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shtanko.entity.card.Card;
import shtanko.repository.CardRepository;

@Service
public class CardService {
    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card getCard(Long numberCard) {
        return cardRepository.findByNumberCard(numberCard);
    }

    public void saveCard(Card card) {
        cardRepository.save(card);
    }
}
