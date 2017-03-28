package org.bugu.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bugu.Context;
import org.bugu.Starter;
import org.bugu.mvc.impl.DefaultViewResolver;
import org.bugu.mvc.impl.ErrorView;
import org.bugu.mvc.impl.NotFoundView;
import org.bugu.util.Toolkit;

/**
 * 前端控制器
 * 
 * @author Administrator
 *
 */
public class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4098384779298032803L;

	private Context context;

	private UrlMapping urlMapping;

	private Handler handler;

	private ViewResolver viewResolver;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		Starter starter = new Starter(config);
		starter.start();
		context = starter.createContext();
		urlMapping = new UrlMapping(context);
		handler = new Handler();
		viewResolver = new DefaultViewResolver();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Action action = urlMapping.mapping(request);
		if (action == null||action.getMethodMapping() == null) {
			View view = new NotFoundView();
			view.render(request, response, null);
			return;
		}
		Object returnValue = null;
		try {
			ActionFilter[] actionFilters = action.getMethodMapping().getActionFilters();
			if (Toolkit.isEmpty(actionFilters)) {
				returnValue = handler.doHandle(request, response, action);
			} else {
				ActionFilterChainCallback callback = new ActionFilterChainCallback(handler, request, response, action);
				ActionFilterChain chain = new ActionFilterChain(actionFilters, action.getControllerMapping().getHost(),
						action.getMethodMapping().getMethod(), callback);
				chain.doChain(request, response);
				returnValue = chain.getReturnValue();
				View view = chain.getView();
				if (view != null) {
					view.render(request, response, returnValue);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ErrorView errorView = new ErrorView();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw, true);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
			String exception = sw.toString();
			errorView.render(request, response, exception);
			return;
		}
		View view = viewResolver.resolver(action);
		view.render(request, response, returnValue);
	}

	class ActionFilterChainCallback implements ActionFilterChain.Callback {

		private Handler handler;
		private HttpServletRequest request;
		private HttpServletResponse response;
		private Action action;

		public ActionFilterChainCallback(Handler handler, HttpServletRequest request, HttpServletResponse response,
				Action action) {
			this.handler = handler;
			this.request = request;
			this.response = response;
			this.action = action;
		}

		@Override
		public Object invoke()throws Exception {
			// TODO Auto-generated method stub
			return handler.doHandle(request, response, action);
		}

	}
}
