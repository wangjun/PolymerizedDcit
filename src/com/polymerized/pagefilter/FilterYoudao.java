package com.polymerized.pagefilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.polymerized.bean.MetaMeaning;

/**
 * 分析有道词典的网页内容，过滤信息之后存储到词典义对象中
 * 
 * @author edgar
 * 
 */
public class FilterYoudao {

	DocumentBuilderFactory builderFactory = DocumentBuilderFactory
			.newInstance();

	@SuppressWarnings("deprecation")
	public List<MetaMeaning> getMeansBykeyword(String keyword) {
		if (keyword == null || keyword.equals(""))
			return null;
		keyword = java.net.URLEncoder.encode(keyword);
		List<MetaMeaning> reList = new ArrayList<MetaMeaning>();
		Document doc = null;

		String url = "http://fanyi.youdao.com/openapi.do?keyfrom=AllDict&key=752015575&type=data&doctype=xml&version=1.1&q="
				+ keyword;
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			doc = builder.parse(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		MetaMeaning meaning = new MetaMeaning();
		// 键
		String keyString = doc.getElementsByTagName("query").item(0)
				.getTextContent();
		// System.out.println(keyString);
		meaning.setKey(keyString);

		// 音标
		Node phnNode = doc.getElementsByTagName("phonetic").item(0);
		if (phnNode != null) {
			String phnString = phnNode.getTextContent();
			// System.out.println(phnString);
			meaning.setPhnmic("[" + phnString + "]");
		}

		// 基本释义
		List<String> baseExplain = new ArrayList<String>();
		NodeList baseNodes = doc.getElementsByTagName("explains").item(0)
				.getChildNodes();
		int length = baseNodes.getLength();
		for (int i = 0; i < length; i++) {
			String ex = baseNodes.item(i).getTextContent().trim();
			if (ex == null || ex.equals(""))
				continue;
			baseExplain.add(ex);
		}
		meaning.setBaseExplain(baseExplain);

		// 扩展释义（网络释义)
		Map<String, String> extMap = new HashMap<String, String>();
		NodeList extNodes = doc.getElementsByTagName("web").item(0)
				.getChildNodes();
		length = extNodes.getLength();
		for (int i = 0; i < length; i++) {
			Node explainNode = extNodes.item(i);

			if (explainNode.getNodeType() != Node.ELEMENT_NODE)
				continue;
			// System.out.println(explainNode.getNodeName());

			NodeList expList = explainNode.getChildNodes();
			String webKeyString = expList.item(1).getChildNodes().item(0)
					.getNodeValue();

			String webValueString = "";
			NodeList valuesList = expList.item(3).getChildNodes();
			for (int k = 0; k < valuesList.getLength(); k++) {
				Node expNode = valuesList.item(k);
				if (expNode.getNodeType() != Node.ELEMENT_NODE)
					continue;
				webValueString += valuesList.item(k).getChildNodes().item(0)
						.getNodeValue()
						+ "; ";
			}
			extMap.put(webKeyString, webValueString);
		}
		meaning.setExample(extMap);
		reList.add(meaning);
		return reList;
	}
}
