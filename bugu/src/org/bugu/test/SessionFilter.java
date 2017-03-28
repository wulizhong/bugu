package org.bugu.test;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bugu.mvc.View;
import org.bugu.mvc.impl.AbstractActionFilter;

public class SessionFilter extends AbstractActionFilter{

	private String key;

	@Override
	public void init(String args) {
		// TODO Auto-generated method stub
		super.init(args);
		key = args;
	}

	@Override
	public View beforeInvoke(HttpServletRequest request, HttpServletResponse response, Object obj, Method method) {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if(session == null||session.getAttribute(key)==null)
			return new SessionMissView();
		return super.beforeInvoke(request, response, obj, method);
	}

	@Override
	public Object afterInvoke(HttpServletRequest request, HttpServletResponse response, Object obj, Object returnObj,
			Method method) {
		// TODO Auto-generated method stub
		return super.afterInvoke(request, response, obj, returnObj, method);
	}

	
}
