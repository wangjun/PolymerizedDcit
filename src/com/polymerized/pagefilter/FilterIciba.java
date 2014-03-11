package com.polymerized.pagefilter;

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

public class FilterIciba implements DictFilter {

	public final String reqUrl = "http://www.iciba.com/";

	@SuppressWarnings("deprecation")
	public List<MetaMeaning> getMeansBykeyword(String keyword) {
		keyword = java.net.URLEncoder.encode(keyword);
		Document doc = null;
		try {
			doc = Jsoup.connect(reqUrl + keyword).get();
		} catch (IOException e) {
			System.out.println("访问地址失败");
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

		Element baseElement = doc.select("div.dictbar").first();

		// // 英文键
		String keyString = baseElement.getElementById("word_name_h1").text();
		// System.out.println(keyString);
		meaning.setKey(keyString);

		// 音标
		Element phnElem = doc.select("div.prons").first();
		String phnString = phnElem.text().replace(" [", "[");
		// System.out.println(phnString);
		meaning.setPhnmic(phnString);

		// 中文简要翻译
		List<String> baseExpList = new ArrayList<String>();
		Element baseExp = baseElement.select("div.group_prons").first();
		// System.out.println(baseExp.text());
		baseExpList.add(baseExp.text());
		baseExp = baseElement.select("div.net_paraphrase").first()
				.select("ul.clear").first();
		baseExpList.add(baseExp.text());
		meaning.setBaseExplain(baseExpList);

		// 扩展释义
		Map<String, String> expMap = new HashMap<String, String>();
		Elements extElements = doc.getElementById("dict_tab_105")
				.getElementsByAttributeValue("class", "part_main cn_main vNet");
		int size = extElements.size();
		for (int i = 1; i < size; i++) {
			Element extExpElement = extElements.get(i);

			String key1 = extExpElement.getElementsByTag("h3").first().text();
			key1 = key1.replace(" ,", ",");
			String key2 = extExpElement.getElementsByTag("h4").first().text();
			key2 = key2.substring(2);
			String value = extExpElement
					.getElementsByAttributeValue("title", "摘要").first().html();
			value = value.replaceAll("<font.*?>", "{");
			value = value.replaceAll("</font*+>", "}");
			value = value.replaceAll("&.+?;", "");
			// System.out.println(key1);
			// System.out.println(key2);
			// System.out.println(value);
			// System.out.println("---------------");
			expMap.put(key1 + key2, value);
		}
		meaning.setExample(expMap);

		relList.add(meaning);

		return relList;
	}
}
