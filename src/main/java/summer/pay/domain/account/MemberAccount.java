package summer.pay.domain.account;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import summer.pay.domain.Member;
import summer.pay.domain.type.AccountType;
import summer.pay.domain.type.BankType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(AccountType.MEMBER)
public class MemberAccount extends Account{
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private String memo;
	@Builder
	public MemberAccount(int deposit, String number, BankType bankType, Member member, String memo) {
		super(deposit, number, bankType);
		this.member = member;
		this.memo = memo;
	}
	public static MemberAccount createMemberAccount(Member member,String number, BankType bankType, String memo){
		return MemberAccount.builder()
			.bankType(bankType)
			.number(number)
			.memo(memo)
			.deposit(0)
			.member(member)
			.build();
	}

}
