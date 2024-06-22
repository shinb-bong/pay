package summer.pay.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CompanyDto {
	private String password;
	private String name;
	private String email;
}
