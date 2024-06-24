package summer.pay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import summer.pay.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	boolean existsByEmail(String email);
	Optional<Member> findByEmail(String email);
}
