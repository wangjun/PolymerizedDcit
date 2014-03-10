package com.polymerized.bean;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 存储词典解释的元对象，有特殊需要的可以继承此类实现
 * 
 * @author edgar
 * 
 */

public class MetaMeaning {
	private String key;

	/** 音标 */
	private String phnmic;

	/** 基本释义 */
	private List<String> baseExplain;

	/** 例句 */
	private Map<String, String> example;

	/** 扩展解释 */
	private String extension;

	public MetaMeaning() {
		key = "";
		phnmic = "";
		extension = "";
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("key:" + key);
		sb.append("\nphonemic:" + phnmic);
		for (String s : baseExplain)
			sb.append("\n" + s);

		Iterator ite = example.entrySet().iterator();
		int i = 1;
		while (ite.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) ite.next();
			sb.append("\n" + i++ + "." + entry.getKey());
			sb.append("\n" + entry.getValue());
		}

		sb.append("\n" + extension);

		return sb.toString();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<String> getBaseExplain() {
		return baseExplain;
	}

	public void setBaseExplain(List<String> baseExplain) {
		this.baseExplain = baseExplain;
	}

	public String getPhnmic() {
		return phnmic;
	}

	public void setPhnmic(String phnmic) {
		this.phnmic = phnmic;
	}

	public Map<String, String> getExample() {
		return example;
	}

	public void setExample(Map<String, String> example) {
		this.example = example;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}
