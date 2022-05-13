package shtanko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shtanko.entity.card.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card findByNumberCard(Long numberCard);
}
