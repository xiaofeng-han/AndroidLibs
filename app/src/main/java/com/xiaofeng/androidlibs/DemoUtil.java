package com.xiaofeng.androidlibs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility for demo item generate
 */
public class DemoUtil {
	private static final Random random = new Random();
	private static final String STRING_BASE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static List<String> generate(int total, int minLen, int maxLen, int maxLines, boolean randomOrder) {
		ArrayList<String> result = new ArrayList<>(total);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < total; i ++) {
			sb.setLength(0);
			int lineCount = random.nextInt(maxLines) + 1;
			List<String> lines = generate(lineCount, minLen, maxLen, randomOrder);
			boolean firstLine = true;
			for (String line : lines) {
				if (firstLine) {
					firstLine = false;
				} else {
					sb.append("\n");
				}
				sb.append(line);
			}
			result.add(sb.toString());
		}
		return result;
	}
	public static List<String> generate(int total, int minLen, int maxLen, boolean randomOrder) {
		int baseLen = STRING_BASE.length();
		ArrayList<String> result = new ArrayList<>(total);
		int baseIndex, len;
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
