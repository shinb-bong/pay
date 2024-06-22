package summer.pay.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import summer.pay.domain.account.CompanyAccount;
import summer.pay.domain.type.Grade;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Company {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Long id;
	private String password;
	private String name; // 기업명
	private String email;

	@OneToOne(mappedBy = "company",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private CompanyAccount ca;

	public static Company createCompany(String password, String name,String email){
		return Company.builder()
			.email(email)
			.name(name)
			.password(password)
			.build();
	}
	public void addCa(CompanyAccount ca){
		this.ca = ca;
	}

}
