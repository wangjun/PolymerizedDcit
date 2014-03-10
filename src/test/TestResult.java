package test;

import java.util.Iterator;
import java.util.List;

import com.polymerized.bean.MetaMeaning;
import com.polymerized.io.NetUtils;
import com.polymerized.pagefilter.FilterDictall;

/**
 * 结果测试类
 * 
 * @author edgar
 * 
 */
public class TestResult {
	public void test() {
		String url = "http://dictall.com/dictall/result.jsp?cd=UTF-8&keyword=$KEYWORD$";
		String pageContent = new NetUtils().getPageByKeywords(url, "欧西精神");
		List<MetaMeaning> expList = new FilterDictall()
				.getMeanFromWebpage(pageContent);
		Iterator<MetaMeaning> meanite = expList.iterator();
		while(meanite.hasNext())
			System.out.println(meanite.next());
	//	ListIterator<MetaMeaning> ite = dictMeaning.listIterator();
		//System.out.println(dictMeaning);
	}

	public static void main(String args[]) {
		new TestResult().test();
	}
}
