package summer.pay.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import summer.pay.domain.account.CompanyAccount;
import summer.pay.domain.account.MemeberAccount;

/*	탐색 방식으로 인한 정산 완료 테이블 따로 제작*/
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PaidSalary {

	@Id
	@Column(name = "paid_salary_id")
	private Long id;
	private int amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_account_id")
	private MemeberAccount memberAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private CompanyAccount companyAccount;

	private LocalDateTime payDate; // 월급시기
	private LocalDateTime completedDate; // 월급 지급 완료시기

	public PaidSalary(Long id,int amount, MemeberAccount memberAccount, CompanyAccount companyAccount, LocalDateTime payDate) {
		this.id = id;
		this.amount = amount;
		this.memberAccount = memberAccount;
		this.companyAccount = companyAccount;
		this.payDate = payDate;
		this.completedDate = LocalDateTime.now();
	}
}
