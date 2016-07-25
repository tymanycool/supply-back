package net.shopin.supply.util;

/**
 * 
* @ClassName: UnicodeStringUtil 
* @author zhangq
* @date 2015-3-9 下午04:13:23 
*
 */
public abstract class UnicodeStringUtil {

	public static String fromUnicodeString(String unicodeString) {
		StringBuilder sb = new StringBuilder();
		StringBuilder left = new StringBuilder(unicodeString);
		while (left.length() > 0) {
			int index = left.indexOf("\\u");
			if (index != -1) {
				sb.append(left.subSequence(0, index));
				left.delete(0, index);
				String unicode = left.substring(2, 6);
				try { 
					char _char = (char) Integer.parseInt(unicode, 16);
					sb.append(_char);
					left.delete(0, 6);
				} catch (NumberFormatException e) {
					sb.append(left.subSequence(0, 2));
					left.delete(0, 2);
				}
			} else {
				sb.append(left);
				left.delete(0, left.length());
			}
		}
		return sb.toString();
	}

}
