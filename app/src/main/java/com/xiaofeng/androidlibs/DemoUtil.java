package com.xiaofeng.androidlibs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by xiaofeng on 1/5/16.
 */
public class DemoUtil {
	private static final Random random = new Random();
	private static final String STRING_BASE = "0123456789";
	public static List<String> generate(int total, int minLen, int maxLen, boolean randomOrder) {
		int baseLen = STRING_BASE.length();
		ArrayList<String> result = new ArrayList<>(total);
		int baseIndex = 0;
		int len = 1;
		for (int i = 0; i < total; i ++) {
			int index = i;
			if (randomOrder) {
				index += random.nextInt(1000);
			}
			baseIndex = index % baseLen;
			len = random.nextInt(maxLen - minLen) + minLen;
			char baseChar = STRING_BASE.charAt(baseIndex);
			char[] chars = new char[len];
			for (int j = 0; j < len; j ++) {
				chars[j] = baseChar;
			}
			result.add(new String(chars));
		}
		return result;
	}
}
