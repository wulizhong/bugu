package org.bugu.aop;

import java.lang.reflect.Method;
import java.util.List;

public class InterceptorChain {

	private List<MethodInterceptor> interceptorList;
	
	private int currentIndex = 0;
	
	private Callback callback;
	
	private boolean isInvoked = false;
	
	private Object returnValue;
	
	private Object callingObj;
	
	private Method callingMethod;
	
	private Object[] callingMethodArgs;
	
	public InterceptorChain(
			List<MethodInterceptor> interceptorList,
			Object obj,
			Method method,
			Object[] args,
			Callback callback){
		this.interceptorList = interceptorList;
		this.callingObj = obj;
		this.callingMethod = method;
		this.callingMethodArgs = args;
		this.callback = callback;
		
	}
	
	public void doChain(){
		if(currentIndex==interceptorList.size()){
			returnValue = callback.invoke();
			isInvoked = true;
		}else{
			currentIndex++;
			interceptorList.get(currentIndex-1).intercept(this);
		}
	}
	
	public interface Callback{
		public Object invoke();
	}

	public boolean isInvoked() {
		return isInvoked;
	}

	public void setInvoked(boolean isInvoked) {
		this.isInvoked = isInvoked;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	public Object getCallingObj() {
		return callingObj;
	}

	public void setCallingObj(Object callingObj) {
		this.callingObj = callingObj;
	}

	public Method getCallingMethod() {
		return callingMethod;
	}

	public void setCallingMethod(Method callingMethod) {
		this.callingMethod = callingMethod;
	}

	public Object[] getCallingMethodArgs() {
		return callingMethodArgs;
	}

	public void setCallingMethodArgs(Object[] callingMethodArgs) {
		this.callingMethodArgs = callingMethodArgs;
	}
	
}
