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

public class FilterBing implements DictFilter {

	public final String reqUrl = "http://cn.bing.com/dict/search?FORM=BDVSP6&q=";

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

		Element rootElem = doc.getElementsByAttributeValue("class", "qdef")
				.first();

		// 英文键
		String keyString = rootElem.getElementById("headword").html();
		keyString = keyString.replaceAll("<.*?>", " ").trim();
		// System.out.println(keyString);
		meaning.setKey(keyString);

		// 音标
		Element phnElem = rootElem.select("div.hd_tf_lh").first();
		String phnString = phnElem.html().replaceAll("<.*?>", " ").trim();
		phnString = phnString.replaceAll(" +", "");
		phnString = phnString.replaceAll("\n+", " ");
		phnString = phnString.replace("&nbsp;", "");
		// System.out.println(phnString);
		meaning.setPhnmic(phnString);

		// 中文简要翻译
		List<String> baseExpList = new ArrayList<String>();
		Elements baseExpElements = rootElem.getElementsByTag("ul").get(0)
				.children();
		int size = baseExpElements.size();
		for (int i = 0; i < size; i++) {
			Element simpExp = baseExpElements.get(i);
			String expString = simpExp.html().replaceAll("<.*?>", " ");
			expString = expString.replaceAll(" +", " ").trim();
			// System.out.println(i+"===="+expString);
			baseExpList.add(expString);
		}
		meaning.setBaseExplain(baseExpList);

		// 例句
		Map<String, String> expMap = new HashMap<String, String>();
		Elements webExpElements = rootElem.getElementById("webid")
				.getElementsByTag("tbody").first().children();
		size = webExpElements.size();
		for (int i = 0; i < size; i++) {
			Element extExp = webExpElements.get(i);
			String extKeyString = extExp.select("div.p1-1").first().text();
			// System.out.println(i + "-----" + extKeyString);
			String extValueString = extExp.select("div.sen_con").first().html();
			extValueString = extValueString.replace("<strong>", "{");
			extValueString = extValueString.replace("</strong>", "}");
			extValueString = extValueString.replace("\n", "");
			// System.out.println(i + "=====" + extValueString);
			expMap.put(extKeyString, extValueString);
		}
		meaning.setExample(expMap);

		// 扩展内容
		Element expElement = doc.select("div.senDefLink").first();
		Elements expListEelments = expElement.getElementsByTag("a");
		size = expListEelments.size();
		for (int i = 0; i < size; i++)
			expListEelments.get(i).remove();
		expElement.children().get(0).remove();
		expElement.children().get(0).remove();
		String extension = expElement.text().replace(" , ", ", ");
		// System.out.println(extension);
		meaning.setExtension(extension);

		relList.add(meaning);

		return relList;
	}
}
