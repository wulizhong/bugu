package org.bugu.test;

import javax.servlet.http.HttpSession;

import org.bugu.Log;
import org.bugu.ioc.annotation.Res;
import org.bugu.mvc.ModelAndView;
import org.bugu.mvc.annotation.Controller;
import org.bugu.mvc.annotation.Filter;
import org.bugu.mvc.annotation.Result;
@Controller
@Result("json")
@Filter(value = SessionFilter.class,args="user")
public class UserController {
	
	@Res
	private UserService userService;
	
	@Filter
	@Result("jsp")
	public Object login(HttpSession session,User u){
		Log.debug(session.getId());
		Log.debug("user login success!");
		Log.debug(u.getAccount());
		Log.debug(u.getPassword());
		User user = userService.findUserById(1);
		userService.addUser(user);
		ModelAndView mav = new ModelAndView();
		session.setAttribute("user", user);
		mav.setView("../main.jsp");
		mav.addModel("user", user);
		return mav;
	}
	
	public Object addUser(User u){
		Log.debug("UserController.addUser is called");
		return u;
	}
	
}
