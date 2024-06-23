package summer.pay.service.batch;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import summer.pay.domain.PaidSalary;
import summer.pay.domain.Salary;
import summer.pay.repository.PaidSalaryRepository;
import summer.pay.repository.SalaryRepository;

/*
지급 테이블을 만들어서 데이터 저장후 지급
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class PaidConfig {

	private final SalaryRepository salaryRepository; //읽음
	private final PaidSalaryRepository paidSalaryRepository;//작성
	private final PlatformTransactionManager transactionManager;

	@Bean
	public Job makePayJob(JobRepository jobRepository){
		return new JobBuilder("payJob", jobRepository)
			.start(payFirstStep(jobRepository))
			.build();
	}

	@Bean
	public Step payFirstStep(JobRepository jobRepository){
		return new StepBuilder("payFirstStep",jobRepository)
			.<Salary, PaidSalary>chunk(100,transactionManager)
			.reader(salaryReader())
			.processor(salaryPaidSalaryProcessor())
			.writer(salaryWriter())
			.build();
	}

	@Bean
	public ItemReader<Salary> salaryReader() {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime toDate = now.plusHours(1);
		LocalDateTime fromDate = now.minusDays(31);

		// 내역을 지우지말고 수정방식으로 갈지, 계속 입력할지는 고민중
		log.info("toDate = {}, fromDate = {} ", toDate,fromDate);

		return new RepositoryItemReaderBuilder<Salary>()
			.name("salaryReader")
			.repository(salaryRepository)
			.methodName("findAllByPayDateBetween")
			.pageSize(100)
			.arguments(Arrays.asList(fromDate,toDate))
			.sorts(Collections.singletonMap("id", Sort.Direction.DESC))
			.build();
	}

	@Bean
	public ItemProcessor<Salary,PaidSalary> salaryPaidSalaryProcessor(){
		return salary -> new PaidSalary(
			salary.getId(),
			salary.getAmount(),
			salary.getMemberAccount(),
			salary.getCompanyAccount(),
			salary.getPayDate());
	}

	@Bean
	public ItemWriter<PaidSalary> salaryWriter(){
		return items-> items.forEach(
			item ->{
				Salary salary = salaryRepository.findById(item.getId()).orElse(null);
				if (salary != null){
					salaryRepository.delete(salary);
				}
				item.getMemberAccount().plus(salary.getAmount());
				item.getCompanyAccount().minus(salary.getAmount());
				PaidSalary saved = paidSalaryRepository.save(item);
				log.info("배치 성공  = {}",saved.getId());
			}
		);
	}




}
