package org.bugu.mvc.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bugu.mvc.View;

/**
 * 404 页面
 * @author Administrator
 *
 */

public class NotFoundView implements View{

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response, Object obj) {
		// TODO Auto-generated method stub
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}

}
