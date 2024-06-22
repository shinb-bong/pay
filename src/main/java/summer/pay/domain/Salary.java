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
	private LocalDateTime date;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	private Company company;

	private boolean isPayments; // 지급 여부(배치)

	public static Salary createSalary(int amount, LocalDateTime date, Member member, Company company){
		return Salary.builder()
			.amount(amount)
			.date(date)
			.member(member)
			.company(company)
			.isPayments(false)
			.build();
	}

}
