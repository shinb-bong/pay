package summer.pay.service.company;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import summer.pay.controller.dto.CompanyDto;
import summer.pay.domain.Company;
import summer.pay.domain.type.BankType;
import summer.pay.repository.CompanyRepository;
import summer.pay.service.account.AccountService;

@Transactional
@Slf4j
@SpringBootTest
class CompanyServiceTest {

	@Autowired
	CompanyService companyService;

	@Autowired
	CompanyRepository companyRepository;

	@Test
	public void 기업생성및계좌생성() throws Exception {
	    //given
		CompanyDto ibkDto = CompanyDto.builder()
			.email("ibk@ibk.com")
			.password("1234")
			.name("ibk")
			.build();
		//when
		Long companyId = companyService.join(ibkDto);
		//then
		Company company = companyRepository.findById(companyId).get();
		assertThat(company.getEmail()).isEqualTo("ibk@ibk.com");
		assertThat(company.getCa().getNumber()).startsWith("2222");
		assertThat(company.getCa().getDeposit()).isZero();
		assertThat(company.getCa().getBankType()).isEqualTo(BankType.IBK);
	}

}