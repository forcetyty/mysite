package kr.co.itcen.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration					// AOP가 자동으로 설정된다.
@EnableTransactionManagement 	//Dao와 Service에서 상투적인 코드가 발생하지 않도록 함
public class DBConfig {
	
	@Bean
	public DataSource dataSource() {
		//DataBase와 연결되는 객체!!!
		BasicDataSource basicDataSource = new BasicDataSource();
		
		basicDataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		basicDataSource.setUrl("jdbc:mariadb://192.168.1.81:3306/webdb?characterEncoding=utf8");
		basicDataSource.setUsername("webdb");
		basicDataSource.setPassword("webdb");
		basicDataSource.setInitialSize(10);	//동시 접속 가능
		basicDataSource.setMaxActive(100);
		
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
