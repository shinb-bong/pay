package summer.pay.service.company;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import summer.pay.controller.dto.CompanyDto;
import summer.pay.domain.Company;
import summer.pay.domain.account.CompanyAccount;
import summer.pay.domain.type.BankType;
import summer.pay.repository.CompanyRepository;
import summer.pay.service.account.AccountService;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyService {

	private final CompanyRepository companyRepository;
	private final AccountService accountService;
	private final String EMAIL_ERROR = "이메일이 중복되어 만들수 없습니다.";
	private final String NAME_ERROR = "기업 명이 중복되어 만들수 없습니다.";

	/**
	 * 기업은 계좌가 IBK 하나로 고정
	 * 기업 생성 당시 바로 계좌 생성 1:1
	 */
	@Transactional
	public Long join(CompanyDto companyDto){
		BankType ibk = BankType.IBK;
		validateCompany(companyDto.getEmail(), companyDto.getName());
		Company company = Company.createCompany(companyDto.getPassword(), companyDto.getName(), companyDto.getEmail());
		String number = accountService.generateNumber(ibk);
		CompanyAccount ca = CompanyAccount.createCompanyAccount(number, company, ibk);
		company.addCa(ca);
		return companyRepository.save(company).getId();
	}

	private void validateCompany(String email, String name) {
		if(companyRepository.existsByEmail(email))
			throw new IllegalStateException(EMAIL_ERROR);
		if(companyRepository.existsByName(name))
			throw new IllegalStateException(NAME_ERROR);
	}

}
