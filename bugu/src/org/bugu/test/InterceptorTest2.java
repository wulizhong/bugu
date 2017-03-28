package org.bugu.test;

import java.lang.reflect.Method;

import org.bugu.Log;
import org.bugu.aop.AbstractMethodInterceptor;

public class InterceptorTest2 extends AbstractMethodInterceptor{

	@Override
	public boolean beforeInvoke(Object obj, Method method, Object... args) {
		// TODO Auto-generated method stub
		
		Log.debug("InterceptorTest2 beforeInvoke"+method.getName()+" before is called");
		
		return super.beforeInvoke(obj, method, args);
	}

	@Override
	public Object afterInvoke(Object obj, Object returnObj, Method method, Object... args) {
		// TODO Auto-generated method stub
		Log.debug("InterceptorTest2 afterInvoke "+method.getName()+" after is called");
		if(returnObj!=null&&returnObj instanceof User){
			User user = new User();
			user.setAccount("xiugaile");
			return user;
		}
		return super.afterInvoke(obj, returnObj, method, args);
	}

	
}
