package summer.pay.service.account;


import java.util.List;

import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import summer.pay.common.config.util.GenerateNumber;
import summer.pay.domain.Member;
import summer.pay.domain.account.Account;
import summer.pay.domain.account.MemberAccount;
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
	public MemberAccount memberOpen(Long memberId, BankType bankType, String memo) {
		Member member = memberService.findMemberId(memberId);
		String number = generateNumber(bankType);
		MemberAccount memberAccount = MemberAccount.createMemberAccount(member, number, bankType, memo);
		return accountRepository.save(memberAccount);
	}

	public Account find(Long accountId){
		return accountRepository.findById(accountId)
			.orElseThrow(()-> new IllegalStateException("계좌 없음"));
	}

	@Transactional
	public int send(Long sendAccountId,
		String receiverNumber, BankType receiverBankType,
		int money){
		Account sender = findSender(sendAccountId);
		Account receiver = findNumber(receiverNumber,receiverBankType);
		sender.minus(money);
		receiver.plus(money);
		return money;
	}

	private Account findSender(Long sendAccountId) {
		return accountRepository.findById(sendAccountId).orElseThrow(
			()-> new IllegalStateException("입금 계좌가 없습니다.")
		);
	}

	public Account findNumber(String number, BankType bankType) {
		return accountRepository.findByNumberAndBankType(number,bankType).orElseThrow(() -> new IllegalStateException("해당 계좌는 없는 계좌입니다."));
	}

	public List<MemberAccount> findMemberAccounts(Long memberId){
		return accountRepository.findByMemberId(memberId);
	}

	@Retryable(maxAttempts = 10)
	public String generateNumber(BankType bankType) {
		String number = GenerateNumber.generateNumber(bankType);
		boolean exists = accountRepository.existsByNumber(number);
		if (exists)
			throw new IllegalStateException("계좌 생성 갯수 부족");
		return number;
	}

	@Transactional
	public void selfDeposit(Long accountId, int amount){
		Account sender = findSender(accountId);
		sender.plus(amount);
	}

}
