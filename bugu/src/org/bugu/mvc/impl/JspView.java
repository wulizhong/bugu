package org.bugu.mvc.impl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bugu.mvc.ModelAndView;
import org.bugu.mvc.View;

public class JspView implements View{

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response, Object obj) {
		// TODO Auto-generated method stub
		try {
			if(obj!=null&&obj instanceof ModelAndView){
				ModelAndView mav = (ModelAndView) obj;
				Map<String,Object> result = mav.getModelMap();
				String jsp = mav.getView();
				for(Map.Entry<String, Object> entry : result.entrySet()){
					if(!"jsp".equals(entry.getKey())){
						request.setAttribute(entry.getKey(), entry.getValue());
					}
				}
				request.getRequestDispatcher(jsp).forward(request, response);
			}
			
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
