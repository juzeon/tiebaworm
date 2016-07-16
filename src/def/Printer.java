package def;
class Printer {
	public static long filename=System.currentTimeMillis();
	public void println(Object str){
		System.out.println(str);
	}
	public void print(Object str){
		System.out.print(str);
	}
}
