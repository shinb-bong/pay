package summer.pay.config.util;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import summer.pay.common.config.util.GenerateNumber;
import summer.pay.domain.type.BankType;

@Slf4j
class GenerateNumberTest {
	@Test
	void 숫자생성() throws Exception {
	    //given
		String s = GenerateNumber.generateNumber(BankType.KB);
		//when
		log.info("generate Number = {}", s);
	    //then
		Assertions.assertThat(s).startsWith(BankType.KB.getCode());
	}

}