package test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.frame.Service;
import com.vo.UserVO;

public class Userdelete {

	public static void main(String[] args) {
		// Start Spring Container
		AbstractApplicationContext factory
		= new GenericXmlApplicationContext("myspring.xml");
		
		Service<String,UserVO> biz = 
			(Service)factory.getBean("uservice");
		String user = "id66";
		try {
			biz.remove(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		factory.close();
	}
}



