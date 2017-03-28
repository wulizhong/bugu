package org.bugu.test;

import org.bugu.Log;
import org.bugu.aop.annotation.Interceptor;
import org.bugu.ioc.annotation.Bean;

@Bean

public class UserService {

	@Interceptor({InterceptorTest.class,InterceptorTest2.class})
	public User findUserById(int id){
		Log.debug("findUserById is called");
		User u = new User();
		u.setAccount("zhangsan");
		u.setPassword("123456");
		return u;
	}
	
	public void addUser(User u){
		Log.debug("addUser is called");
	}
}
