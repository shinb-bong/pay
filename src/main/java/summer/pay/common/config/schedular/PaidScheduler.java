package summer.pay.common.config.schedular;

import java.util.Date;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import summer.pay.service.batch.PaidConfig;

@Component
@Slf4j
@RequiredArgsConstructor
public class PaidScheduler {
	private final JobLauncher jobLauncher;
	private final PaidConfig paidConfig;
	private final JobRepository jobRepository;
	@Scheduled(cron="0 0 6 14 * *")
	public void runJob(){
		JobParameters jobParameters = new JobParametersBuilder()
			.addDate("date", new Date(System.currentTimeMillis()))
			.toJobParameters();
		try {
			jobLauncher.run(paidConfig.makePayJob(jobRepository), jobParameters);
		} catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
				 | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
			log.info(e.getMessage());
		}
	}
}
