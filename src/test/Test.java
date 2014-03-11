package test;

public class Test {

	public static void main(String[] args) {
		String str = "<strong>a</strong>b";
		str = str.replaceAll("<.*?>", " ");
		System.out.println(str);
	}

}
