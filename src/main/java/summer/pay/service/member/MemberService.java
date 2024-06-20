package summer.pay.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import summer.pay.controller.dto.MemberDto;
import summer.pay.domain.Member;
import summer.pay.repository.MemberRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final String MEMBER_NOT_FOUND = "회원을 찾지 못했습니다.";
	private final String MEMBER_DUPLICATED = "회원이 존재합니다.";

	@Transactional
	public Long join(MemberDto memberDto){
		validateMember(memberDto.getEmail());
		Member member = Member.createMember(memberDto.getPassword(), memberDto.getName(), memberDto.getEmail());
		return memberRepository.save(member).getId();
	}

	public Member findMember(Long memberId){
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalStateException(MEMBER_NOT_FOUND));
	}

	private void validateMember(String email){
		boolean b = memberRepository.existsByEmail(email);
		if (b== true)
			throw new IllegalStateException(MEMBER_DUPLICATED);
	}
}
