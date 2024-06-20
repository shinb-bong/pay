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
import summer.pay.domain.type.BankType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public abstract class Account {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int deposit;
	private String number;
	@Enumerated(EnumType.STRING)
	private BankType bankType;

}
