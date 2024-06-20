package summer.pay.domain.account;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import summer.pay.domain.type.BankType;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public abstract class Account {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int deposit;
	private String number;
	@Enumerated(EnumType.STRING)
	private BankType bankType;

	public Account(int deposit, String number, BankType bankType) {
		this.deposit = deposit;
		this.number = number;
		this.bankType = bankType;
	}
}
