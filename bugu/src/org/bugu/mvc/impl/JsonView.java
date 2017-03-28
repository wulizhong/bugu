package org.bugu.mvc.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bugu.mvc.View;

import com.alibaba.fastjson.JSON;

public class JsonView implements View{

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response, Object obj) {
		// TODO Auto-generated method stub
		String jsonStr = "";
		if(obj!=null){
			jsonStr = JSON.toJSONString(obj);
		}
		try {
			response.getWriter().append(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
