package br.com.fcoinvest.invest.config;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;




@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "fcoinvestEntityManager", transactionManagerRef = "fcoinvestTransactionManager", basePackages = {
		"br.com.fcoinvest.invest.repositories" })
public class FcoinvestBackPostgreSqlConfig {
	
	@Bean(name = "fcoinvestDataSource")
	@Primary
	@ConfigurationProperties(prefix = "fcoinvest.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "fcoinvestEntityManager")
	@Primary
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("fcoinvestDataSource") DataSource dataSource) {
		HashMap<String, Object> props = new HashMap<>();
		props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		return builder.dataSource(dataSource).properties(props).persistenceUnit("fcoinvest")
				.packages("br.com.fcoinvest.invest.models")
				.build();
	}

	@Bean(name = "fcoinvestTransactionManager")
	@Primary
	public JpaTransactionManager transactionManager(
			@Qualifier("fcoinvestEntityManager") EntityManagerFactory fcoinvestEntityManager) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(fcoinvestEntityManager);
		return transactionManager;
	}

}
