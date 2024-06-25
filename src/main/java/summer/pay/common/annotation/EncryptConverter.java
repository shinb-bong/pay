package summer.pay.common.annotation;

import java.util.Set;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import summer.pay.common.Encryption;

/**
 * Converter: 하나의 타입을 다른 타입으로 변환하는 단순한 역할
 * 즉, PathVariable에 사용
 */
public class EncryptConverter implements ConditionalGenericConverter {
	@Override
	public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
		return targetType.hasAnnotation(EncryptId.class);
	}

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return Set.of(
			new ConvertiblePair(Long.class,String.class)
		);
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		try {
			return Encryption.encrypt((Long)source);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage());
		}
	}
}
