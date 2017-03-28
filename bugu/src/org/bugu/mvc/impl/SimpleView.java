package org.bugu.mvc.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bugu.mvc.View;

public class SimpleView implements View{

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response, Object obj) {
		// TODO Auto-generated method stub
		try {
			response.getWriter().append("login success!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
