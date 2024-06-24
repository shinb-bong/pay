package summer.pay.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import summer.pay.domain.account.Account;
import summer.pay.domain.account.MemberAccount;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	boolean existsByNumber(String number);
	Optional<Account> findByNumber(String number);

	List<MemberAccount> findByMemberId(Long memberId);

}
