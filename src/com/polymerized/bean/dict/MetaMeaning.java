package com.polymerized.bean.dict;

import java.util.List;
import java.util.Map;

/**
 * 存储词典解释的元对象，有特殊需要的可以继承此类实现
 * 
 * @author edgar
 * 
 */

public class MetaMeaning {
	private String key;

	/** 基本释义 */
	private List<String> baseExplain;

	/** 音标 */
	private String phnmic;

	/** 例句 */
	private Map<String, String> example;

	/** 扩展解释 */
	private String extension;

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
