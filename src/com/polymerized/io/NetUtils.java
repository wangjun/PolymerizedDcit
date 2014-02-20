package com.polymerized.io;

/**
 * 联网工具类，实现不同的请求格式均可以返回有效的网页内容
 * 
 * @author edgar
 * 
 */
public class NetUtils {
	/**
	 * 通过统一资源定位符返回网页内容
	 */
	public String getPageByUri(String uri) {
		return null;
	}

	/**
	 * 替换urlPattern里的$KEYWORD$返回网页内容
	 */
	public String getPageByKeywords(String urlPattern, String keyword) {
		String uri = urlPattern.replace("$KEYWORD$", keyword);
		return getPageByUri(uri);
	}
}
