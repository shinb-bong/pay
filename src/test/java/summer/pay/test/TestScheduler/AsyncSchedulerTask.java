package summer.pay.test.TestScheduler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableAsync
@EnableScheduling
public class AsyncSchedulerTask {

	@Async
	@Scheduled(cron = "0/1 * * * * ?")
	public void schedulerTest1() throws InterruptedException {
		log.info("SchedulerTask.schedulerTest1");
		Thread.sleep(2000);
		log.info("END scheduler1 = {}", Thread.currentThread().getName());
	}

	@Async
	@Scheduled(cron = "0/1 * * * * ?")
	public void schedulerTest2() {
		log.info("SchedulerTask.schedulerTest2");
		log.info("scheduler2 = {}", LocalDateTime.now());
	}
}
