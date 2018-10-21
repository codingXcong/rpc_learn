package cn.zgc.mall;

import cn.zgc.mall.order.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class MainApplication {

	private static Logger logger = LoggerFactory.getLogger(MainApplication.class);

    @SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("consumer.xml");
		OrderServiceImpl orderService = applicationContext.getBean(OrderServiceImpl.class);
		orderService.initOrder("1");
		logger.info("调用完成....");
		System.in.read();
	}
}