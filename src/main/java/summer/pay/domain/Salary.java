package summer.pay.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import summer.pay.domain.account.CompanyAccount;
import summer.pay.domain.account.MemberAccount;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Salary {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "salary_id")
	private Long id;
	private int amount;
	private LocalDateTime payDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_account_id")
	private MemberAccount memberAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private CompanyAccount companyAccount;

	public static Salary createSalary(int amount, LocalDateTime date, MemberAccount ma, CompanyAccount ca){
		return Salary.builder()
			.amount(amount)
			.payDate(date)
			.memberAccount(ma)
			.companyAccount(ca)
			.build();
	}

}
