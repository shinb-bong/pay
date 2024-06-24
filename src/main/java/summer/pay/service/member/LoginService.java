package summer.pay.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import summer.pay.domain.Member;
import summer.pay.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {
	private final MemberRepository memberRepository;

	public Member login(String email, String password){
		return memberRepository.findByEmail(email)
			.filter(m -> m.getPassword().equals(password))
			.orElse(null);
	}

}
