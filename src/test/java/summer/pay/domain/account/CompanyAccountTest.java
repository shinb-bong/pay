package summer.pay.domain.account;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import summer.pay.domain.Company;
import summer.pay.domain.type.BankType;

@Slf4j
class CompanyAccountTest {

	@Test
	public void 회사_계좌_개설() throws Exception {
	    //given
		Company company = Company.createCompany("1234", "wj", "wj@wj.net");
		BankType kb = BankType.KB;
		//when
		CompanyAccount ca = CompanyAccount.createCompanyAccount(company, kb);
		//then
		assertThat(ca.getDeposit()).isEqualTo(0);
		assertThat(ca.getBankType()).isEqualTo(kb);
		assertThat(ca.getCompany().getName()).isEqualTo("wj");
	}

}