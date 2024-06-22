package summer.pay.domain.account;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	@Builder
	public CompanyAccount(int deposit, String number, BankType bankType,Company company) {
		super(deposit, number, bankType);
		this.company = company;
	}

	public static CompanyAccount createCompanyAccount(String number, Company company, BankType bankType){
		CompanyAccount ca = CompanyAccount.builder()
			.bankType(bankType)
			.number(number)
			.deposit(0)
			.build();
		ca.addCompany(company);
		return ca;
	}

	public void addCompany(Company company){
		this.company = company;
		company.addCa(this);
	}
}
