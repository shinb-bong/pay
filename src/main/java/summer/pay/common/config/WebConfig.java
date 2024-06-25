package summer.pay.common.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import summer.pay.common.annotation.DecryptConverter;
import summer.pay.common.annotation.EncryptConverter;
import summer.pay.common.annotation.LoginMemberArgumentResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new LoginMemberArgumentResolver());
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new DecryptConverter());
		registry.addConverter(new EncryptConverter());
	}
}
