package summer.pay.domain.type;

public enum BankType {
	KB("1000"),IBK("2222"),ETC("9999");
	private String code;
	private BankType(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
}
