package summer.pay.service.member;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import summer.pay.controller.dto.MemberDto;
import summer.pay.service.MemberTestEx;

@SpringBootTest
@Slf4j
class MemberServiceTest {

	@Autowired
	private MemberService memberService;
	@Test
	public void 회원_가입_중복() throws Exception {
	    //given
		MemberDto dto1 = MemberTestEx.createMemberDto("wj", "wj@wj.com", "1234");
		MemberDto dto2 = MemberDto.builder()
			.name("wj2")
			.email("wj@wj.com")
			.password("12334").build();
		//when
		memberService.join(dto1);
		//then
		assertThrows(IllegalStateException.class, ()-> memberService.join(dto2));
	}
}