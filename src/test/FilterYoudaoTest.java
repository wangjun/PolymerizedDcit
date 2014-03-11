package test;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.polymerized.bean.MetaMeaning;
import com.polymerized.pagefilter.FilterYoudao;

public class FilterYoudaoTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetMeansBykewword() {
		List<MetaMeaning> list = new FilterYoudao().getMeansBykeyword("maven");
		Iterator<MetaMeaning> ite = list.iterator();
		while (ite.hasNext())
			System.out.println(ite.next().toString());
	}

}
