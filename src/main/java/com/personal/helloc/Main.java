package com.personal.helloc;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.rpc.RpcException;
import com.personal.hello.service.DemoService;
import com.personal.hello.service.ValidationService;
import com.personal.hello.service.validate.ValidationParameter;

/**
 * Hello world!
 *
 */

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "classpath:consumer.xml",
				"classpath:applicationContext.xml" });
		context.start();
		DemoService demoService = (DemoService) context.getBean("demoService"); // 获取远程服务代理
		String hello = demoService.sayHello("world"); // 执行远程方法
		log.info("结果：=============" + hello); // 显示调用结果
		log.info("========================end!=========================");

		ValidationService validationService = (ValidationService) context.getBean("validationService");
		try {
			ValidationParameter parameter = new ValidationParameter();
			validationService.save(parameter);
			log.info("Validation ERROR");
		} catch (RpcException e) { // 抛出的是RpcException
			ConstraintViolationException ve = (ConstraintViolationException) e.getCause(); // 里面嵌了一个ConstraintViolationException
			Set<ConstraintViolation<?>> violations = ve.getConstraintViolations(); // 可以拿到一个验证错误详细信息的集合
			System.out.println(violations);
		}
		
		ValidationParameter parameter = new ValidationParameter();
		parameter.setEmail("zhang1@126.com");
		parameter.setName("lidfd");
		parameter.setAge(19);
		validationService.save(parameter);
		validationService.delete(1);

	}

}
