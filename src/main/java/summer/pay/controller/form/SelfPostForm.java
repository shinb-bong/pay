package summer.pay.controller.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import summer.pay.domain.account.Account;
import summer.pay.domain.type.BankType;

@Data
@NoArgsConstructor
public class SelfPostForm {
	private Long accountId;
	private String number;
	private BankType bankType;
	private int amount;

	public SelfPostForm(Account account) {
		this.accountId = account.getId();
		this.number = account.getNumber();
		this.bankType = account.getBankType();
		this.amount = 0;
	}
}
