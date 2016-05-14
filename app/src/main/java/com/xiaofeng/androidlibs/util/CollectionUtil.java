package com.xiaofeng.androidlibs.util;

/**
 * Created by xhan on 5/14/16.
 */
public class CollectionUtil {
	public static <T> boolean isEmpty(T[] arr) {
		if (arr == null || arr.length == 0) {
			return true;
		}
		return false;
	}
}
