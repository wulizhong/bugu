package org.bugu.mvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionFilterChain {

	private ActionFilter[] actionFilterList;
	
	private int currentIndex = 0;
	
	private Callback callback;
	
	private boolean isInvoked = false;
	
	private Object returnValue;
	
	private Object callingObj;
	
	private Method callingMethod;
	
	private View view;
	
	
	
	public ActionFilterChain(
			ActionFilter[] interceptorList,
			Object obj,
			Method method,
			Callback callback){
		this.actionFilterList = interceptorList;
		this.callingObj = obj;
		this.callingMethod = method;
		this.callback = callback;
		
	}
	
	public void doChain(HttpServletRequest request, HttpServletResponse response)throws Exception{
		if(currentIndex==actionFilterList.length){
			returnValue = callback.invoke();
			isInvoked = true;
		}else{
			currentIndex++;
			view = actionFilterList[currentIndex-1].filter(request,response,this);
		}
	}
	
	public interface Callback{
		public Object invoke() throws Exception;
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

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}
	
}
