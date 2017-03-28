package org.bugu.mvc.impl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bugu.mvc.ActionFilter;
import org.bugu.mvc.ActionFilterChain;
import org.bugu.mvc.View;

public class AbstractActionFilter implements ActionFilter{

	@Override
	public View filter(HttpServletRequest request, HttpServletResponse response, ActionFilterChain chain) throws Exception {
		// TODO Auto-generated method stub
		View view = beforeInvoke(request,response,chain.getCallingObj(),chain.getCallingMethod());
		if(view == null){
			chain.doChain(request, response);
			Object newReturnValue = afterInvoke(request,response,chain.getCallingObj(),chain.getReturnValue(),chain.getCallingMethod());
			chain.setReturnValue(newReturnValue);
		}
		return view;
	}	
	
	@Override
	public void init(String args) {
		// TODO Auto-generated method stub
		
	}
	
	public View beforeInvoke(HttpServletRequest request, HttpServletResponse response,Object obj, Method method) {
        return null;
    }
    
    public Object afterInvoke(HttpServletRequest request, HttpServletResponse response,Object obj, Object returnObj, Method method) {
        return returnObj;
    }

}
