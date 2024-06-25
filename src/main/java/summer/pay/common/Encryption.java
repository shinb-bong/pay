package summer.pay.common;

import java.nio.ByteBuffer;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class Encryption {

	private static String ALGORITHM = "AES";
	private static String SECRET;

	@Value("${encryption.secret}")
	private String secretValue;

	@PostConstruct
	public void init() {
		SECRET = secretValue;
	}

	private static SecretKey generateKey() {
		byte[] bytes = SECRET.getBytes();
		return new SecretKeySpec(bytes, ALGORITHM);
	}

	public static String encrypt(final Long value) {
		try{
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, generateKey());
			byte[] valueBytes = ByteBuffer.allocate(Long.BYTES).putLong(value).array();
			byte[] encrypted = cipher.doFinal(valueBytes);
			return Base64.getEncoder().encodeToString(encrypted);
		} catch (Exception e){
			throw new IllegalStateException("인코딩에 문제가 생겼습니다",e);
		}

	}

	public static Long decrypt(final String code){
		try{
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, generateKey());
			byte[] encryptedBytes = Base64.getDecoder().decode(code);
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
			return ByteBuffer.wrap(decryptedBytes).getLong();
		} catch (Exception e){
			throw new IllegalStateException("디코딩에 문제가 생겼습니다.",e);
		}
	}
}
