package test;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.polymerized.bean.MetaMeaning;
import com.polymerized.pagefilter.FilterBing;

public class FilterBingTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetMeansBykeyword() {
		List<MetaMeaning> list = new FilterBing().getMeansBykeyword("shoot");
		Iterator<MetaMeaning> ite = list.iterator();
		while (ite.hasNext())
			System.out.println(ite.next());
	}

}
