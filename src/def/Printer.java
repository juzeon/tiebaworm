package def;
class Printer {
	public static long filename=System.currentTimeMillis();
	public static StringBuilder sb=new StringBuilder();
	public void println(Object str){
		System.out.println(str);
	}
	public void print(Object str){
		System.out.print(str);
	}
}
