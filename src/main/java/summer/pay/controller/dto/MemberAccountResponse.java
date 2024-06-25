package summer.pay.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import summer.pay.common.Encryption;
import summer.pay.domain.account.MemberAccount;
import summer.pay.domain.type.BankType;

@Data
public class MemberAccountResponse {

	private String id; // 인코딩됨
	private String number;
	private BankType bankType;
	private int deposit;
	private String memo;
	private Long memberId;

	public MemberAccountResponse(MemberAccount memberAccount) {
		this.id = Encryption.encrypt(memberAccount.getId());
		this.number = memberAccount.getNumber();
		this.bankType = memberAccount.getBankType();
		this.deposit= memberAccount.getDeposit();
		this.memo=memberAccount.getMemo();
		this.memberId = memberAccount.getMember().getId();
	}

}
