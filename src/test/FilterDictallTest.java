package test;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import com.polymerized.bean.MetaMeaning;
import com.polymerized.pagefilter.FilterDictall;

public class FilterDictallTest {
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetMeanFromWebpage() {
		List<MetaMeaning> expList = new FilterDictall().getMeansBykeyword("匹配");
		Iterator<MetaMeaning> meanite = expList.iterator();
		while (meanite.hasNext())
			System.out.println(meanite.next());
	}

}
