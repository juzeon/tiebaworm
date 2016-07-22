package def;
class Printer {
	public static String filename=System.currentTimeMillis()+".json";
	public static StringBuilder sb=new StringBuilder();
	public void println(Object str){
		System.out.println(str);
	}
	public void print(Object str){
		System.out.print(str);
	}
}
