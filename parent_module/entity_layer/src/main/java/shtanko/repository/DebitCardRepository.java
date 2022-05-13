package shtanko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shtanko.entity.card.DebitCard;

@Repository
public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {
    DebitCard findByNumberCard(Long numberCard);
}
