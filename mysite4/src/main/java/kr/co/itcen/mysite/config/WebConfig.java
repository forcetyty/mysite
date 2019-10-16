package kr.co.itcen.mysite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import kr.co.itcen.config.web.MVCConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"kr.co.itcen.mysite.controller"})
@Import({MVCConfig.class, })
public class WebConfig {
	

}
