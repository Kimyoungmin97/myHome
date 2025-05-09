package com.ssafy.home.common.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

	/**
	 * model 패키지에 속한 클래스의 모든 메서드를 지정하는 pointcut 메서드를 만들자
	 */
	@Pointcut("execution(* com.ssafy..model..*(..))")
	void allModelMethod() {}
	
	/**
	 * 실제메서드의 호출 전, 호출되는 메서드의 클래스명.메서드명을 출력하고 전달하는 파라미터의 값을 로그로 찍는 Advice를 만들자.
	 * pointcut은 위에서 만든 메서드로 지정하자. 
	 */
	@Before("allModelMethod()")
	public void beforeCall(JoinPoint jp) {
		log.trace("호출:{}, 파라미터:{}", 
				jp.getSignature().toShortString(), 
				Arrays.toString( jp.getArgs() ));
	}
	
}

