package kr.co.itcen.mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;


@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"kr.co.itcen.mysite.service", "kr.co.itcen.mysite.repository", "kr.co.itcen.mysite.aspect"})
@Import({})
public class AppConfig {
	

}
