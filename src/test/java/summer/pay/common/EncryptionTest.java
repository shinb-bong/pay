package summer.pay.common;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class EncryptionTest {

	@Test
	public void 암호화_복호화() throws Exception {
	    //given
	    Long itemId = 11L;
	    //when
		String encrypt = Encryption.encrypt(itemId);
		log.info("ENCODE STRING = {}", encrypt);
		Long decrypt = Encryption.decrypt(encrypt);
		log.info("DECODE LONG = {}",decrypt);
		//then
		assertThat(decrypt).isEqualTo(itemId);
	}
}