package shtanko.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shtanko.entity.account.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByNumberAccount(String numberAccount);
}
