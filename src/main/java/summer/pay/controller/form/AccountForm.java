package summer.pay.controller.form;

import lombok.Data;
import summer.pay.domain.type.BankType;

@Data
public class AccountForm {
	private BankType type;
	private String memo;
}
