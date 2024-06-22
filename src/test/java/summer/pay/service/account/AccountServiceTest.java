package summer.pay.service.account;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import summer.pay.controller.dto.MemberDto;
import summer.pay.domain.account.Account;
import summer.pay.domain.account.MemeberAccount;
import summer.pay.domain.type.BankType;
import summer.pay.service.MemberTestEx;
import summer.pay.service.member.MemberService;

@SpringBootTest
@Slf4j
@Transactional
class AccountServiceTest {

	@Autowired
	private AccountService accountService;
	@Autowired
	private MemberService memberService;

	@Test
	void 개인_계좌_생성() throws Exception {
	    //given
		MemberDto dto = MemberTestEx.createMemberDto("wj", "wj@wj.com", "1234");
		Long memberId = memberService.join(dto);
		BankType kb = BankType.KB;
		//when
		String number = accountService.memberOpen(memberId, kb);
		MemeberAccount account = (MemeberAccount) accountService.findByNumber(number);
		//then
		assertThat(account.getMember().getId()).isEqualTo(memberId);
		assertThat(account.getBankType()).isEqualTo(BankType.KB);
		assertThat(account.getNumber()).startsWith(kb.getCode());
	}

	@Test
	void 개인_계좌_송금() throws Exception {
		int money = 10000;
	    //given
		MemberDto dto = MemberTestEx.createMemberDto("wj", "wj@wj.com", "1234");
		Long memberId = memberService.join(dto);
		BankType kb = BankType.KB;
		String senderNumber= accountService.memberOpen(memberId, kb);
		MemberDto dto2 = MemberTestEx.createMemberDto("bk", "bk@bk.com", "12345");
		Long memberId2 = memberService.join(dto2);
		BankType ibk = BankType.IBK;
		String receiverNumber = accountService.memberOpen(memberId2, ibk);

		Account sendAccount = accountService.findByNumber(senderNumber);
		int initMoney = 100000;
		sendAccount.plus(initMoney);
		//when
		int send = accountService.send(senderNumber, receiverNumber, money);
		//then
		Assertions.assertThat(send).isEqualTo(money);
		Assertions.assertThat(accountService.findByNumber(receiverNumber).getDeposit()).isEqualTo(money);
		Assertions.assertThat(sendAccount.getDeposit()).isEqualTo(initMoney-money);
	}

}