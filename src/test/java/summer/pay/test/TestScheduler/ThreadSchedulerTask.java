package summer.pay.test.TestScheduler;

import static org.assertj.core.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
	"schedules.cron.test=0/1 * * * * ?"
})
public class ThreadSchedulerTask {

	@Test
	public void 쓰레드_스케줄러_테스트() {
		Awaitility.await().atMost(10, TimeUnit.SECONDS).untilAsserted(() ->
			assertThat(isTaskExecuted).isTrue()
		);
	}

	private static volatile boolean isTaskExecuted = false;

	@Scheduled(cron = "${schedules.cron.test}")
	public void schedulerTest() throws InterruptedException {
		log.info("schedule START current thread : {}", Thread.currentThread().getName());
		Thread.sleep(3000);
		log.info("schedule END current thread : {}", Thread.currentThread().getName());
		isTaskExecuted = true;
	}

	@TestConfiguration
	static class SchedulerTestConfig implements SchedulingConfigurer {

		@Override
		public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
			ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
			threadPoolTaskScheduler.setPoolSize(2);
			threadPoolTaskScheduler.setThreadGroupName("thread pool for scheduler test");
			threadPoolTaskScheduler.setThreadNamePrefix("my-test-thread");
			threadPoolTaskScheduler.initialize();

			taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
		}

		@Bean
		public ThreadSchedulerTask threadSchedulerTask() {
			return new ThreadSchedulerTask();
		}
	}
}
