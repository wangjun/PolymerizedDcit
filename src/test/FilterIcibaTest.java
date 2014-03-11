package test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.polymerized.bean.MetaMeaning;
import com.polymerized.pagefilter.FilterIciba;
import com.polymerized.pagefilter.FilterYoudao;

public class FilterIcibaTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetMeansBykeyword() {
		List<MetaMeaning> list = new FilterIciba().getMeansBykeyword("maven");
		Iterator<MetaMeaning> ite = list.iterator();
		while (ite.hasNext())
			System.out.println(ite.next().toString());
	}

}
