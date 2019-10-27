package kr.co.itcen.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExecutionTimeAspect {

	// AOP 적용 코드
	// Aroud / Before / After
	// 각 시점마다 다르다
	@Around("execution(* *..controller.*.*(..)) || execution(* *..service.*.*(..)) || execution(* *..repository.*.*(..))")
	public Object aroundAspect(ProceedingJoinPoint pjp) throws Throwable{
		//before
		StopWatch sw = new StopWatch();
		sw.start();
		
		Object result = pjp.proceed();
		
		//after
		sw.stop();
		Long totalTime = sw.getTotalTimeMillis();
		
		// 어떤 클래스의 어떤 메소드
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String taskName = className + "." + methodName;
		
		System.out.println("[Execution time][" + taskName + "] : " +totalTime + "mills");
		
		return result;
		
	}

}
