package org.bugu.mvc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Handler {

	/**
	 * 反射调用Controller的方法。并生成方法参数
	 * @param request
	 * @param response
	 * @param action
	 * @return
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 */
	public Object doHandle(HttpServletRequest request, HttpServletResponse response, Action action) throws IllegalArgumentException, IllegalAccessException, InstantiationException, InvocationTargetException {
		Object returnValue = null;
			Method method = action.getMethodMapping().getMethod();
			Class<?> classes[] = method.getParameterTypes();
			java.lang.reflect.Parameter parameters[] = method.getParameters();
			Object[] args = new Object[classes.length];
			for (int i = 0; i < classes.length; i++) {
				Class<?> clazz = classes[i];
				if (clazz == HttpSession.class) {
					args[i] = request.getSession();
					continue;
				} else if (clazz == HttpServletRequest.class) {
					args[i] = request;
					continue;
				} else if (clazz == HttpServletResponse.class) {
					args[i] = response;
					continue;
				}

				//参数是一个自定义的对象
				if (!isBaseType(clazz)) {
					Class<?> argClass = parameters[i].getType();
					Object arg = argClass.newInstance();
					Field fields[] = argClass.getDeclaredFields();
					for (Field f : fields) {
						Class<?> fieldType = f.getType();
						//更改属性的访问权限
						f.setAccessible(true);
						String fieldName = f.getName();
						if (fieldType == String.class) {
							f.set(arg, request.getParameter(fieldName));
						} else if (fieldType == Long.class) {
							f.setLong(arg, Long.parseLong(request.getParameter(fieldName)));
						} else if (fieldType == Integer.class) {
							f.setInt(arg, Integer.parseInt(request.getParameter(fieldName)));
						} else if (fieldType == Double.class) {
							f.setDouble(arg, Double.parseDouble(request.getParameter(fieldName)));
						} else if (fieldType == Float.class) {
							f.setFloat(arg, Float.parseFloat(request.getParameter(fieldName)));
						} else if (fieldType == Short.class) {
							f.setShort(arg, Short.parseShort(request.getParameter(fieldName)));
						} else if (fieldType == Byte.class) {
							f.setByte(arg, Byte.parseByte(request.getParameter(fieldName)));
						}
						f.setAccessible(false);
					}
					args[i] = arg;
				} else {
					org.bugu.mvc.annotation.Parameter parameterClass = parameters[i]
							.getAnnotation(org.bugu.mvc.annotation.Parameter.class);
					String parameterName = null;
					if (parameterClass != null) {
						parameterName = parameterClass.value();
						if (clazz == String.class) {
							args[i] = request.getParameter(parameterName);
						} else if (clazz == Long.class) {
							args[i] = Long.parseLong(request.getParameter(parameterName));
						} else if (clazz == Integer.class) {
							args[i] = Integer.parseInt(request.getParameter(parameterName));
						} else if (clazz == Double.class) {
							args[i] = Double.parseDouble(request.getParameter(parameterName));
						} else if (clazz == Float.class) {
							args[i] = Float.parseFloat(request.getParameter(parameterName));
						} else if (clazz == Short.class) {
							args[i] = Short.parseShort(request.getParameter(parameterName));
						} else if (clazz == Byte.class) {
							args[i] = Byte.parseByte(request.getParameter(parameterName));
						}
					}
				}

			}
			returnValue = method.invoke(action.getControllerMapping().getHost(), args);
		return returnValue;
	}

	private boolean isBaseType(Class<?> clazz) {
		return clazz == String.class || clazz == Long.class || clazz == Integer.class || clazz == Double.class
				|| clazz == Float.class || clazz == Short.class || clazz == Byte.class;

	}
}
