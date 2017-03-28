package org.bugu.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface View {

	public void render(HttpServletRequest request, HttpServletResponse response,Object obj);
}
