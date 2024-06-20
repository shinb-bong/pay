package summer.pay.service;

import summer.pay.controller.dto.MemberDto;

public class MemberTestEx {
	public static MemberDto createMemberDto(String name, String email, String password){
		return MemberDto.builder()
			.name(name)
			.email(email)
			.password(password).build();
	}


}
