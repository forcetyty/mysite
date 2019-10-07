package kr.co.itcen.mysite.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//어노테이션 인터페이스는 앞에 @이를 붙인다. //ElementType.METHOD
@Target({ElementType.TYPE ,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	//public enum Role{USER, ADMIN};
	
	//public Role role() default Role.USER;		//택 1 @Auth(role=Auth.Role.ADMIN)
	
	public String value() default "USER";		//택 1 @Auth("USER")	//default "USER" //default "ADMIN"
	
	//public String role() default "ADMIN";
	
	//public int test() default 1;

}
