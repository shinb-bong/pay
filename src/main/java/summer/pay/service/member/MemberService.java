package summer.pay.service.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import summer.pay.common.config.MemberConst;
import summer.pay.controller.dto.MemberDto;
import summer.pay.domain.Member;
import summer.pay.repository.MemberRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public Long join(MemberDto memberDto){
		validateMember(memberDto.getEmail());
		Member member = Member.createMember(memberDto.getPassword(), memberDto.getName(), memberDto.getEmail());
		return memberRepository.save(member).getId();
	}

	public Member findMemberId(Long memberId){
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalStateException(MemberConst.MEMBER_NOT_FOUND));
	}

	private void validateMember(String email){
		if(memberRepository.existsByEmail(email))
			throw new IllegalStateException(MemberConst.MEMBER_DUPLICATED);
	}
}
