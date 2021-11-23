package test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.frame.Service;
import com.vo.UserVO;



public class Userinsert {

	public static void main(String[] args) {
		// Start Spring Container
		AbstractApplicationContext factory
		= new GenericXmlApplicationContext("myspring.xml");
		
		Service<String,UserVO> biz = 
			(Service)factory.getBean("uservice");
		UserVO user = new UserVO("id999","pwd999","99999999999999");
		try {
			biz.register(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		factory.close();
	}
}



