package shtanko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shtanko.entity.transfer.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
