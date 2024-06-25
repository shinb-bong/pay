package summer.pay.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import summer.pay.common.annotation.DecryptId;
import summer.pay.common.annotation.Login;
import summer.pay.controller.dto.MemberAccountResponse;
import summer.pay.controller.form.AccountForm;
import summer.pay.controller.form.SelfPostForm;
import summer.pay.domain.Member;
import summer.pay.domain.account.Account;
import summer.pay.domain.account.MemberAccount;
import summer.pay.domain.type.BankType;
import summer.pay.service.account.AccountService;

@Slf4j
@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

	private final AccountService accountService;

	@GetMapping("/add")
	public String createAccountForm(@ModelAttribute("accountForm") AccountForm accountForm, Model model){
		log.info("createAccount");
		model.addAttribute("bankTypes",BankType.values());
		return "account/createAccountForm";
	}
	@PostMapping("/add")
	public String submitAccountForm(@ModelAttribute("accountForm") AccountForm accountForm, @Login Member member) {
		MemberAccount memberAccount = accountService.memberOpen(member.getId(), accountForm.getType(),
			accountForm.getMemo());
		log.info("Selected Bank Type: " + accountForm.getType());
		log.info("CREATED NUMBER = {}", memberAccount.getMember());
		return "redirect:/";
	}

	@GetMapping
	public String findAccounts(@Login Member member, Model model){
		List<MemberAccount> memberAccounts = accountService.findMemberAccounts(member.getId());
		List<MemberAccountResponse> memberAccountResponseList = memberAccounts.stream()
			.map(MemberAccountResponse::new)
			.toList();
		model.addAttribute("accounts", memberAccountResponseList);
		return "account/accountList";
	}

	@GetMapping("/post/self/{accountId}")
	public String selfPostForm(@PathVariable @DecryptId Long accountId, Model model){
		Account account = accountService.find(accountId);
		SelfPostForm form = new SelfPostForm(account);
		model.addAttribute("selfPostForm", form);
		return "account/selfPostForm";
	}

	@PostMapping("/post/self")
	public String handleSelfPostForm(SelfPostForm selfPostForm) {
		accountService.selfDeposit(selfPostForm.getAccountId(), selfPostForm.getAmount());
		return "redirect:/accounts"; // 성공 페이지로 리다이렉트
	}
}
