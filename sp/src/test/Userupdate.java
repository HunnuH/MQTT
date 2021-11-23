package test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.frame.Service;
import com.vo.UserVO;

public class Userupdate {

	public static void main(String[] args) {
		// Start Spring Container
		AbstractApplicationContext factory
		= new GenericXmlApplicationContext("myspring.xml");
		
		Service<String,UserVO> biz = 
			(Service)factory.getBean("uservice");
		UserVO user =new UserVO("id66","pw00","±è±è");
		try {
			biz.modify(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		factory.close();
	}
}



