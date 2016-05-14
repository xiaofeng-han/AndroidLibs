package com.xiaofeng.androidlibs.util;

/**
 * Created by xhan on 5/14/16.
 */
public class ArrayUtil {
	public static final int NOT_FOUND = -1;
	public static <T extends Comparable> int indexOf(T[] arr, T searchKey) {
		if (CollectionUtil.isEmpty(arr)) {
			return NOT_FOUND;
		}
		int index = 0;
		for (T arrItem : arr) {
			if (arrItem.compareTo(searchKey) == 0) {
				return index;
			}
			index ++;
		}
		return NOT_FOUND;
	}
}
