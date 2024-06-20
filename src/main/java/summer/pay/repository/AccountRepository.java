package summer.pay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import summer.pay.domain.account.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

	boolean existsByNumber(String number);
}
