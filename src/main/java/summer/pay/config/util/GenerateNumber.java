package summer.pay.config.util;

import java.util.random.RandomGenerator;

import summer.pay.domain.type.BankType;

public class GenerateNumber {
	public static String generateNumber(BankType bankType){
		StringBuilder sb = new StringBuilder();
		sb.append(bankType.getCode());
		RandomGenerator gen = RandomGenerator.of("L128X256MixRandom");
		for (int i = 0; i < 3; i++) {
			sb.append("-");
			sb.append(gen.nextInt(999,9999));
		}
		return sb.toString();
	}
}
