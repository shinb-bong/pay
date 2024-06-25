package summer.pay.controller.form;

import lombok.Data;
import summer.pay.domain.type.BankType;

@Data
public class PostForm {

	private String senderNumber;
	private BankType senderBankType;
	private String receiverNumber;
	private BankType receiverBankType;
	private int amount;
}
