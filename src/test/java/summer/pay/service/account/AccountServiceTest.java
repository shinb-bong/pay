package summer.pay.service.account;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import summer.pay.controller.dto.MemberDto;
import summer.pay.domain.account.MemeberAccount;
import summer.pay.domain.type.BankType;
import summer.pay.service.MemberTestEx;
import summer.pay.service.member.MemberService;

@SpringBootTest
@Slf4j
class AccountServiceTest {

	@Autowired
	private AccountService accountService;
	@Autowired
	private MemberService memberService;

	@Test
	public void 개인_계좌_생성() throws Exception {
	    //given
		MemberDto dto = MemberTestEx.createMemberDto("wj", "wj@wj.com", "1234");
		Long memberId = memberService.join(dto);
		BankType kb = BankType.KB;
		//when
		Long accountId= accountService.memberOpen(memberId, kb);
		MemeberAccount account = (MemeberAccount) accountService.find(accountId);
		//then
		assertThat(account.getMember().getId()).isEqualTo(memberId);
		assertThat(account.getBankType()).isEqualTo(BankType.KB);
		assertThat(account.getNumber()).startsWith(kb.getCode());
	}

}