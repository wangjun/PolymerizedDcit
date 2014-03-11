package test;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.polymerized.bean.MetaMeaning;
import com.polymerized.pagefilter.FilterUrbandict;

public class FilterUrbandictTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		List<MetaMeaning> list = new FilterUrbandict()
				.getMeansFromFile("/home/edgar/桌面/a.html");
		Iterator<MetaMeaning> ite = list.iterator();
		while (ite.hasNext()) {
//			System.out.println(ite.next());
		}
	}

}
