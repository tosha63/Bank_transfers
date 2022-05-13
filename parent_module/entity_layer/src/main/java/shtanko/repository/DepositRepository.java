package shtanko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shtanko.entity.deposit.Deposit;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
}
