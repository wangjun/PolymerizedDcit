package com.polymerized.pagefilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.polymerized.bean.DictFilter;
import com.polymerized.bean.MetaMeaning;

public class FilterUrbandict implements DictFilter {

	public final String reqUrl = "http://www.urbandictionary.com/define.php?term=";

	@SuppressWarnings("deprecation")
	public List<MetaMeaning> getMeansBykeyword(String keyword) {
		keyword = java.net.URLEncoder.encode(keyword);
		Document doc = null;
		try {
			doc = Jsoup.connect(reqUrl + keyword).timeout(5000).get();
		} catch (IOException e) {
			System.out.println("访问地址失败");
			e.printStackTrace();
		}
		return getMeanFromJsoupDoc(doc);
	}

	public List<MetaMeaning> getMeansFromFile(String filename) {
		File file = new File(filename);
		Document doc = null;
		try {
			doc = Jsoup.parse(file, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getMeanFromJsoupDoc(doc);
	}

	/**
	 * 通过一个Jsoup对象返回MetaMeaning对象列表
	 * 
	 * @param doc
	 */
	private List<MetaMeaning> getMeanFromJsoupDoc(Document doc) {

		List<MetaMeaning> relList = new ArrayList<MetaMeaning>();
		MetaMeaning meaning = new MetaMeaning();

		Element rootElem = doc.getElementById("content");

		Elements expsElements = rootElem.select("div.box");
		int size = expsElements.size();
		for (int i = 0; i < size; i++) {
			if (i == 1)
				continue;
			Element expElement = expsElements.get(i);

			// 英文键
			String key = expElement.select("div.word").first().text();
			System.out.println(key);
			meaning.setKey(key);

			// 扩展解释
			Map<String, String> expMap = new HashMap<String, String>();
			String keyString = expElement.select("div.meaning").first().text();
			System.out.println(keyString);
			String valueString = expElement.select("div.example").first()
					.text();
			System.out.println(valueString);
			expMap.put(keyString, valueString);
			meaning.setExample(expMap);
			relList.add(meaning);
		}
		return relList;
	}
}
