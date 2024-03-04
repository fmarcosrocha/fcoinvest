package br.com.fcoinvest.invest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeansAuxiliaresConfig {

	private static final Logger log = LoggerFactory.getLogger(BeansAuxiliaresConfig.class);

	@Bean(name = "encrypterEncoder")
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		log.info("Configurando Password Encoder");
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public PageableHandlerMethodArgumentResolverCustomizer paginationCustomizer() {
//		return pageableResolver -> {
//			pageableResolver.setMaxPageSize(20000);
//		};
//	}
}
