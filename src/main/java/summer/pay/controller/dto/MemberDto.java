package summer.pay.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import summer.pay.domain.type.Grade;

@Getter
@Builder
@AllArgsConstructor
public class MemberDto {
	private String password;
	private String name;
	private String email;
	private Grade grade;
}
