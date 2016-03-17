/**
 * 
 */
package com.personal.helloc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.personal.hello.service.DemoService;

/**
 * @author liuquan
 *
 */
@Component
public class Task implements ApplicationContextAware{
	
	private static final Logger log = LoggerFactory.getLogger(Task.class);
	
	private static ApplicationContext context = null;
	
	@Scheduled(cron = "*/15 * * * * ?")
	public void execute(){
		if(context!=null){
			DemoService demoService = (DemoService)context.getBean("demoService"); // 获取远程服务代理
		    String hello = demoService.sayHello("world"); // 执行远程方法
		    log.info("task结果：============="+hello); // 显示调用结果
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

}
