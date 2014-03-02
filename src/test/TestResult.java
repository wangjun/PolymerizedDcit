package test;

import com.polymerized.bean.dict.MetaMeaning;
import com.polymerized.display.dict.DictToWeb;
import com.polymerized.io.FileUtils;
import com.polymerized.io.NetUtils;
import com.polymerized.pagefilter.dict.FilterDictall;

/**
 * 结果测试类
 * 
 * @author edgar
 * 
 */
public class TestResult {
	public void test() {
		String url = "http://dictall.com/dictall/result.jsp?cd=UTF-8&keyword=$KEYWORD$";
		String pageContent = new NetUtils().getPageByKeywords(url, "扩展");
		MetaMeaning dictMeaning = new FilterDictall()
				.getMeanFromWebpage(pageContent);
		System.out.println(dictMeaning);
	}

	public static void main(String args[]) {
		new TestResult().test();
	}
}
