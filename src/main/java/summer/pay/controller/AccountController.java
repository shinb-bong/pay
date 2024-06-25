package summer.pay.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import summer.pay.common.annotation.Login;
import summer.pay.controller.form.AccountForm;
import summer.pay.domain.Member;
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
		// 계좌 생성 로직을 여기에 추가하세요
		String number = accountService.memberOpen(member.getId(), accountForm.getType(), accountForm.getMemo());
		log.info("Selected Bank Type: " + accountForm.getType());
		log.info("CREATED NUMBER = {}", number);
		return "redirect:/";
	}

	@GetMapping
	public String findAccounts(@Login Member member, Model model){
		List<MemberAccount> memberAccounts = accountService.findMemberAccounts(member.getId());
		model.addAttribute("accounts", memberAccounts);
		return "account/accountList";
	}
}
