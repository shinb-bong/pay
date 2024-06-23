package summer.pay.test.TestScheduler;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@SpringBootTest(properties = {
	"schedules.cron.test=0/2 * * * * ?"
	})
public class SchedulerTask {
	List<LocalDateTime> arrayList = new ArrayList<>() ;
	List<LocalDateTime> arrayList2 = new ArrayList<>() ;

	@Test
	public void 스케쥴러_테스트() throws Exception {
	    //given
		assertThat(arrayList).isEmpty();
	    //when
		Awaitility.await()
			.atMost(6, TimeUnit.SECONDS)
			.untilAsserted(()-> {
					// assertThat(arrayList).isNotEmpty(); // 1초 진행 후에 검사
					assertThat(arrayList).hasSize( 3);
				}
			);
	    //then
	    
	}

	/**
	 * 동기식 스케줄러 테스트
	 * 현재는 2가 끝나고 나서야 1이 실행
	 */
	@Scheduled(cron = "${schedules.cron.test}")
	public void schedulerTest1(){
		arrayList.add(LocalDateTime.now());
		log.info("SchedulerTask.schedulerTest1");
		log.info("END scheduler1 = {}", LocalDateTime.now());
	}

	@Scheduled(cron = "${schedules.cron.test}")
	public void schedulerTest2(){
		arrayList2.add(LocalDateTime.now());
		System.out.println("SchedulerTask.schedulerTest2");
		log.info("scheduler2 = {}", LocalDateTime.now());
	}
}
