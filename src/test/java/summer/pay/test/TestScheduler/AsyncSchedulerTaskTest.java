package summer.pay.test.TestScheduler;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AsyncSchedulerTaskTest {

	@Autowired
	private AsyncSchedulerTask asyncSchedulerTask;

	@Test
	public void testScheduler() throws Exception {
		// Given
		Thread.sleep(10000);
		// Optionally, you can add more assertions to validate the contents of the lists
	}
}
