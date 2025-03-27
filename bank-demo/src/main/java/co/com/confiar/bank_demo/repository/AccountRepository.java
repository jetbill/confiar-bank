package co.com.confiar.bank_demo.repository;

import co.com.confiar.bank_demo.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Boolean existsAccountByAccountNumber(String accountNumber);
    Optional<Account> findByAccountNumber(String accountNumber);
}
