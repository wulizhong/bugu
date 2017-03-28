package org.bugu.util;

import java.util.List;
/**
 * 工具类
 * @author Administrator
 *
 */
public class Toolkit {
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str == null)
			return true;
		return str.isEmpty();
	}
	/**
	 * 判断数组是否为空
	 * @param objs
	 * @return
	 */
	public static boolean isEmpty(Object[] objs){
		if(objs == null)
			return true;
		return objs.length==0;
	}
	/**
	 * 判断List是否为空
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list){
		if(list == null)
			return true;
		return list.isEmpty();
	}
}
