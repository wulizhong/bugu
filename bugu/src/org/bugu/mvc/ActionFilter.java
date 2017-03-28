package org.bugu.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionFilter {
	public View filter(HttpServletRequest request, HttpServletResponse response,ActionFilterChain chain)throws Exception;
	public void init(String args);
}
