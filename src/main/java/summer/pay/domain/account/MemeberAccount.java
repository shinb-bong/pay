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
import summer.pay.config.util.GenerateNumber;
import summer.pay.domain.Member;
import summer.pay.domain.type.AccountType;
import summer.pay.domain.type.BankType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue(AccountType.MEMBER)
public class MemeberAccount extends Account{
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public MemeberAccount(int deposit, String number, BankType bankType, Member member) {
		super(deposit, number, bankType);
		this.member = member;
	}
	public static MemeberAccount createMemberAccount(Member member, BankType bankType){
		return MemeberAccount.builder()
			.bankType(bankType)
			.number(GenerateNumber.generateNumber(bankType))
			.deposit(0)
			.member(member)
			.build();
	}

}
