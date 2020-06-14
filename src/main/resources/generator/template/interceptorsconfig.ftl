package ${basePackageValue}.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器
 */
@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {

	/*代码分界head TODO*/
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(AuthorityInterceptor()).addPathPatterns("/**")
				.excludePathPatterns("/swagger-resources/**","/swagger-ui.html","/v2/api-docs","/webjars/**","/doc.html");
	}

	@Bean
	public AuthorityInterceptor AuthorityInterceptor() {
		return new AuthorityInterceptor();
	}

	/*代码分界end TODO*/

}