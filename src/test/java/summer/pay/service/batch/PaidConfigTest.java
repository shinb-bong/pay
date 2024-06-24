package summer.pay.service.batch;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import lombok.extern.slf4j.Slf4j;
import summer.pay.domain.PaidSalary;
import summer.pay.domain.Salary;
import summer.pay.domain.account.CompanyAccount;
import summer.pay.domain.account.MemberAccount;
import summer.pay.domain.type.BankType;
import summer.pay.repository.AccountRepository;
import summer.pay.repository.PaidSalaryRepository;
import summer.pay.repository.SalaryRepository;

@EnableAutoConfiguration
@SpringBootTest
@SpringBatchTest
@Slf4j
class PaidConfigTest {

	@Autowired
	JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	SalaryRepository salaryRepository;
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	PaidSalaryRepository paidSalaryRepository;

	private final String MEMBER_NUMBER ="1111-1111-1111-1111";
	private final String COMPANY_NUMBER ="2222-2222-2222-2222";

	@Test
	public void 잡_실행() throws Exception {
		//given
		JobParameters jobParameters = new JobParametersBuilder()
			.addDate("date", new Date(System.currentTimeMillis()))
			.toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		assertThat(jobExecution.getExitStatus())
			.isEqualTo(ExitStatus.COMPLETED);
		assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
	}
	
	@Test
	@Rollback
	public void paidJob_test() throws Exception {
	    //given
		MemberAccount account = MemberAccount.createMemberAccount(null,MEMBER_NUMBER , BankType.KB);
		MemberAccount savedMA = accountRepository.save(account);

		CompanyAccount account2 = CompanyAccount.builder()
			.bankType(BankType.IBK)
			.deposit(0)
			.company(null)
			.number(COMPANY_NUMBER)
			.build();
		CompanyAccount savedCA = accountRepository.save(account2);

		// 월급 저장
		LocalDateTime now = LocalDateTime.now();
		for (int i = 0; i <40; i++) {
			Salary salary = Salary.createSalary(1000 * i, now.minusDays(i), savedMA, savedCA);
			salaryRepository.save(salary);
		}
	    //when
		JobParameters jobParameters = new JobParametersBuilder()
			.addDate("date", new Date(System.currentTimeMillis()))
			.toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		//then
		List<PaidSalary> all = paidSalaryRepository.findAll();
		assertThat(all).hasSize(32);
		assertThat(salaryRepository.findAll()).hasSize(8);
		log.info(all.get(0).toString());
	    
	}


}