package summer.pay.service.account;


import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import summer.pay.config.util.GenerateNumber;
import summer.pay.domain.Member;
import summer.pay.domain.account.Account;
import summer.pay.domain.account.MemeberAccount;
import summer.pay.domain.type.BankType;
import summer.pay.repository.AccountRepository;
import summer.pay.service.member.MemberService;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

	private final MemberService memberService;
	private final AccountRepository accountRepository;

	@Transactional
	public String memberOpen(Long memberId, BankType bankType) {
		Member member = memberService.findMember(memberId);
		String number = generateNumber(bankType);
		MemeberAccount memberAccount = MemeberAccount.createMemberAccount(member, number, bankType);
		return accountRepository.save(memberAccount).getNumber();
	}

	public Account find(Long accountId){
		return accountRepository.findById(accountId)
			.orElseThrow(()-> new IllegalStateException("계좌 없음"));
	}

	@Transactional
	public int send(String sendNumber, String receiverNumber,int money){
		Account sender = findByNumber(sendNumber);
		Account receiver = findByNumber(receiverNumber);
		sender.minus(money);
		receiver.plus(money);
		return money;
	}

	public Account findByNumber(String number) {
		return accountRepository.findByNumber(number).orElseThrow(() -> new IllegalStateException("해당 계좌는 없는 계좌입니다."));
	}

	@Retryable(maxAttempts = 10)
	public String generateNumber(BankType bankType) {
		String number = GenerateNumber.generateNumber(bankType);
		boolean exists = accountRepository.existsByNumber(number);
		if (exists)
			throw new IllegalStateException("계좌 생성 갯수 부족");
		return number;
	}

}
