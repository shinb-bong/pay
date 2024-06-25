package summer.pay.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //타겟은 파라미터 레벨
@Retention(RetentionPolicy.RUNTIME) //런타임까지 어노테이션 정보가 남아있음
public @interface Login {
}
