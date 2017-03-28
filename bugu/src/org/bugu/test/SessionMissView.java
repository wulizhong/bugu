package org.bugu.test;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bugu.Log;
import org.bugu.mvc.View;

public class SessionMissView implements View{

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response, Object obj) {
		// TODO Auto-generated method stub
		try {
			Log.debug("please login!");
			response.getWriter().append("please login!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
