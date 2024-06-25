package summer.pay.common.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import summer.pay.controller.LoginController;
import summer.pay.domain.Member;

/**
 * ArgumentResolver: 요청과 관련된 복잡한 로직을 처리할 수 있으며,
 * 주로 HTTP 요청과 관련된 데이터를 처리
 */
@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		log.info("supportsParameter");
		boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
		boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());
		return hasLoginAnnotation && hasMemberType;

	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		HttpSession session = request.getSession(false);
		if (session == null){
			return null;
		}
		return session.getAttribute(LoginController.LOGIN_MEMBER);
	}
}
