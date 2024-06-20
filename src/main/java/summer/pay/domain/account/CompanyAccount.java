package summer.pay.domain.account;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import summer.pay.domain.Company;
import summer.pay.domain.type.AccountType;
import summer.pay.domain.type.BankType;

@Entity
@Getter
@DiscriminatorValue(AccountType.COMPANY)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyAccount extends Account{

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@Builder
	public CompanyAccount(int deposit, String number, BankType bankType,Company company) {
		super(deposit, number, bankType);
		this.company = company;
	}

	public static CompanyAccount createCompanyAccount(String number, Company company, BankType bankType){
		return CompanyAccount.builder()
			.bankType(bankType)
			.number(number)
			.deposit(0)
			.company(company)
			.build();
	}
}
