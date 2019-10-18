package kr.co.itcen.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 20191017 수업내용
@Configuration					// AOP가 자동으로 설정된다.
@EnableTransactionManagement 	//Dao와 Service에서 상투적인 코드가 발생하지 않도록 함
@PropertySource("classpath:kr/co/itcen/config/app/properties/jdbc.properties")
public class DBConfig {
	
	@Autowired
	private Environment env;	//프로퍼티 설정
	
	@Bean
	public DataSource dataSource() {
		//DataBase와 연결되는 객체!!!
		
		BasicDataSource basicDataSource = new BasicDataSource();
		
		basicDataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		basicDataSource.setUrl(env.getProperty("jdbc.url"));
		basicDataSource.setUsername(env.getProperty("jdbc.username"));
		basicDataSource.setPassword(env.getProperty("jdbc.password"));
		basicDataSource.setInitialSize(env.getProperty("jdbc.initialSize", Integer.class));	//동시 접속 가능
		basicDataSource.setMaxActive(env.getProperty("jdbc.maxActive", Integer.class));
		
		return basicDataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		// 하나에 Thread가 만들어지면 하나에 연결이 생성된다.
		// Thread = Tx = connection
		// controller => Service => Dao
		// Container가 Thread를 관리해줌.
		// 서로 다른 트랜잭션이라고 하면 서로 다르게 동작한다.
		// 하나에 쓰레드 안에서 연결이 생성된고, 생성된 연결을 통해서 동작한다.
		return new DataSourceTransactionManager(dataSource);
		
		
	}
	

}
