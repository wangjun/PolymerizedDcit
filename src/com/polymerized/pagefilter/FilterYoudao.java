package com.polymerized.pagefilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import com.polymerized.bean.MetaMeaning;

/**
 * 分析有道词典的网页内容，过滤信息之后存储到词典义对象中
 * 
 * @author edgar
 * 
 */
public class FilterYoudao {
	/**
	 * 通过一个Jsoup对象返回MetaMeaning对象列表
	 * 
	 * @param doc
	 */
	private List<MetaMeaning> getMeanFromJsoupDoc(Document doc) {
		List<MetaMeaning> relList = new ArrayList<MetaMeaning>();
		MetaMeaning meaning = null;

		Elements elems = doc.getElementById("catelist").children();
		ListIterator<Element> ite = elems.listIterator();
		while (ite.hasNext()) {
			Element element = ite.next();
			meaning = new MetaMeaning();

			Element en = element.select("div.en").first();
			// 英文键
			String keyString = en.child(0).html();
			keyString = keyString.substring(keyString.lastIndexOf("p;") + 2,
					keyString.length());
			// System.out.println(keyString);
			meaning.setKey(keyString);

			// 音标
			String phnString = en.select("span.pho").html();
			phnString = phnString.replace("&nbsp;", " ");
			// System.out.println(phnString);
			meaning.setPhnmic(phnString);

			// 中文简要翻译
			Element cn = element.select("div.cn").first();
			// System.out.println(cn.html());
			List<String> baseExp = new ArrayList<String>();
			baseExp.add(cn.html());
			meaning.setBaseExplain(baseExp);

			// 例句
			Elements en_sen = element.select("div.en_sen");
			Elements cn_sen = element.select("div.cn_sen");
			int size = en_sen.size();
			Map<String, String> expMap = new HashMap<String, String>();
			for (int i = 0; i < size; i++) {
				String enString = en_sen.get(i).html();
				int endtagIndex = enString.indexOf("<s");
				if (endtagIndex != -1)
					enString = enString.substring(0, enString.indexOf("<s"));
				enString = enString.trim();
				enString = enString.replace("\n", "");
				// System.out.println("--" + i + "---" + enString);
				String cnString = cn_sen.get(i).html();
				cnString = cnString.trim();
				cnString = cnString.replace("\n", "");
				// System.out.println("--" + i + "---" + cnString);
				expMap.put(enString, cnString);
			}
			meaning.setExample(expMap);

			// 扩展内容
			Element expUrl = element.select("div.more_st").first();
			if (expUrl != null) {
				meaning.setExtension("host"
						+ expUrl.select("a").first().attr("href"));
			}
			relList.add(meaning);
		}
		return relList;
	}

	/**
	 * 通过分析网页内容返回词典义
	 * 
	 * @param pageContent
	 */
	public List<MetaMeaning> getMeanFromWebpage(String pageContent) {
		if (pageContent == null || pageContent.equals(""))
			return null;
		Document doc = Jsoup.parse(Jsoup.clean(pageContent, Whitelist.basic()));
		return getMeanFromJsoupDoc(doc);
	}

	/**
	 * 从网络地址加载内容
	 * 
	 */
	public List<MetaMeaning> getMeanFromUrl(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			System.out.println("访问地址失败");
			e.printStackTrace();
		}
		return getMeanFromJsoupDoc(doc);
	}

	/**
	 * 从文件加载
	 */
	public List<MetaMeaning> getMeanFromFile(String filename) {
		Document doc = null;
		File file = new File(filename);
		try {
			doc = Jsoup.parse(file, "UTF-8", null);
			Cleaner cleaner = new org.jsoup.safety.Cleaner(Whitelist.basic());
			doc = cleaner.clean(doc);
		} catch (IOException e) {
			System.out.println("打开文件失败");
			e.printStackTrace();
		}
		return getMeanFromJsoupDoc(doc);
	}
}
