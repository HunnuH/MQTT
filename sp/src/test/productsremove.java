package test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.frame.Service;
import com.vo.Product;



public class productsremove {

	public static void main(String[] args) {
		// Start Spring Container
		AbstractApplicationContext factory
		= new GenericXmlApplicationContext("myspring.xml");
		
		Service<Integer,Product> biz = 
			(Service)factory.getBean("pbiz");
		int product = 105;
		try {
			biz.remove(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		factory.close();
	}
}



