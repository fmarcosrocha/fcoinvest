package br.com.fcoinvest.invest.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {
	private static final Logger log = LoggerFactory.getLogger(LocaleConfig.class);

	@Bean
	public LocaleResolver localeResolver() {
		log.info("Capturando locale");
		SessionLocaleResolver slr = new SessionLocaleResolver();
		final Locale ptBRLocale = new Locale("pt", "BR");
		LocaleContextHolder.setDefaultLocale(ptBRLocale);
		Locale.setDefault(new Locale("pt", "BR"));
		slr.setDefaultLocale(ptBRLocale);
		return slr;
	}
}
