package test;

import java.util.Date;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.frame.Service;
import com.vo.Product;



public class productsinsert {
	
	public static void main(String[] args) {
		
		// Start Spring Container
		AbstractApplicationContext factory
		= new GenericXmlApplicationContext("myspring.xml");
		
		Service<Integer,Product> biz = 
			(Service)factory.getBean("pbiz");
		Product product = new Product("pants04",40000,"pants04.jpg");
		try {
			biz.register(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		factory.close();
	}

}



