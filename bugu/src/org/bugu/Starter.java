package org.bugu;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;

import org.bugu.ioc.Ioc;
import org.bugu.mvc.ActionFilter;
import org.bugu.mvc.ControllerMapping;
import org.bugu.mvc.MethodMapping;
import org.bugu.mvc.annotation.Controller;
import org.bugu.mvc.annotation.Filter;
import org.bugu.mvc.annotation.RequestMapping;
import org.bugu.mvc.annotation.Result;

public class Starter {

	private ServletConfig config;
	private ArrayList<ControllerMapping> controllerMappings;

	public Context createContext() {
		Context mvcContext = new Context();
		mvcContext.setControllerMappings(controllerMappings);
		return mvcContext;
	}

	public Starter(ServletConfig config) {
		this.config = config;
		controllerMappings = new ArrayList<ControllerMapping>();
	}

	public void start() {
		Log.infor("////////////////start//////////////////");
		File rootPath = new File(config.getServletContext().getRealPath("/WEB-INF/classes/"));
		ArrayList<String> classNamesWidthPath = new ArrayList<String>();
		findAllClass(rootPath, classNamesWidthPath);
		ArrayList<String> classNames = new ArrayList<String>();
		Log.infor(" start scan file ");
		for (String n : classNamesWidthPath) {
			n = n.substring(config.getServletContext().getRealPath("/WEB-INF/classes/").length());
			n = n.replace("\\", ".");
			n = n.replace(".class", "");
			Log.infor(n);
			classNames.add(n);
		}

		// 查找合适的类
		for (String n : classNames) {
			try {
				Class<?> clazz = Class.forName(n);
				Controller controller = clazz.getAnnotation(Controller.class);
				RequestMapping controllerRequestMapping = clazz.getAnnotation(RequestMapping.class);

				if (controller != null) {
					ControllerMapping cm = new ControllerMapping();
					if (controllerRequestMapping == null) {
						String className = clazz.getSimpleName();
						String str = String.valueOf(className.charAt(0));
						className = className.replaceFirst(str, str.toLowerCase());
						cm.setUrl("/" + className);
					} else {
						cm.setUrl(controllerRequestMapping.value());
					}

					Object host = Ioc.getInstance().get(clazz);
					cm.setHost(host);

					for (Method method : clazz.getDeclaredMethods()) {
						RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
						MethodMapping mm = new MethodMapping();
						if (methodRequestMapping != null) {
							mm.setUrl(methodRequestMapping.value());
						} else {
							mm.setUrl("/" + method.getName());
						}
						Result methodResultMapping = method.getAnnotation(Result.class);
						if (methodResultMapping != null) {
							mm.setResult(methodResultMapping.value());
						} else {
							Result classResultMapping = clazz.getAnnotation(Result.class);
							if (classResultMapping != null) {
								mm.setResult(classResultMapping.value());
							}
						}

						Filter methodFilter = method.getAnnotation(Filter.class);
						if (methodFilter != null) {
							Class<?> actionFilters[] = methodFilter.value();
							String args[] = methodFilter.args();
							ActionFilter[] actionFilterArray = createActionFilterArray(actionFilters, args);
							mm.setActionFilters(actionFilterArray);
						} else {
							Filter classFilter = clazz.getAnnotation(Filter.class);
							if (classFilter != null) {
								Class<?> actionFilters[] = classFilter.value();
								String args[] = classFilter.args();
								ActionFilter[] actionFilterArray = createActionFilterArray(actionFilters, args);
								mm.setActionFilters(actionFilterArray);
							}
						}
						Log.infor(cm.getUrl() + mm.getUrl() + " is mapping to " + clazz.getSimpleName() + "."
								+ method.getName());
						mm.setMethod(method);
						cm.addMethodMapping(mm);
						controllerMappings.add(cm);
					}

				}

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private ActionFilter[] createActionFilterArray(Class<?> actionFilters[], String args[]) {
		ActionFilter actionFilterArray[] = new ActionFilter[actionFilters.length];
		for (int i = 0; i < actionFilters.length; i++) {
			
			Class<?> actionFilterClass = actionFilters[i];
			
			actionFilterArray[i] = (ActionFilter) Ioc.getInstance().get(actionFilterClass);
			
			if (i < args.length) {
				actionFilterArray[i].init(args[i]);
			} else {
				actionFilterArray[i].init(null);
			}

		}
		return actionFilterArray;
	}

	/**
	 * 递归调用，获取指定目录下的所有.clas结尾的文件
	 * 
	 * @param file
	 * @param list
	 */
	private void findAllClass(File file, List<String> list) {
		if (file.isFile()) {
			if (file.getName().endsWith(".class"))
				list.add(file.getPath());
		} else {
			for (File f : file.listFiles()) {
				findAllClass(f, list);
			}
		}
	}
}
