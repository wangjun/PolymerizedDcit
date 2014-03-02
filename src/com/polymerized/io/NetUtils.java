package com.polymerized.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

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
		// TODO 当网页响应时间过长时如何处理
		if (uri == null || uri.equals(""))
			return null;

		StringBuffer sb = new StringBuffer();

		try {
			URL url = new URL(uri);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	/**
	 * 替换urlPattern里的$KEYWORD$返回网页内容
	 */
	public String getPageByKeywords(String urlPattern, String keyword) {
		keyword = java.net.URLEncoder.encode(keyword);
		String uri = urlPattern.replace("$KEYWORD$", keyword);
		return getPageByUri(uri);
	}
}
