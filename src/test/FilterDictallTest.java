package test;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.polymerized.bean.MetaMeaning;
import com.polymerized.io.NetUtils;
import com.polymerized.pagefilter.FilterDictall;

public class FilterDictallTest {
	String url = "http://dictall.com/dictall/result.jsp?cd=UTF-8&keyword=$KEYWORD$";

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetMeanFromWebpage() {
		String pageContent = new NetUtils().getPageByKeywords(url, "欧西精神");
		List<MetaMeaning> expList = new FilterDictall()
				.getMeanFromWebpage(pageContent);
		Iterator<MetaMeaning> meanite = expList.iterator();
		while (meanite.hasNext())
			System.out.println(meanite.next());
	}

	@Test
	public void testGetMeanFromUrl() {
		List<MetaMeaning> expList = new FilterDictall().getMeanFromUrl(url);
		Iterator<MetaMeaning> meanite = expList.iterator();
		while (meanite.hasNext())
			System.out.println(meanite.next());
	}

	@Test
	public void testGetMeanFromFile() {
	}

}
