package def;

import java.io.FileWriter;

public class EmptyThread implements Runnable{
	public static boolean[] emptyThread;
	int threadNum;
	FileWriter fw;
	public EmptyThread(int threadNum){
		this.threadNum=threadNum;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		emptyThread=new boolean[threadNum];
		Printer p=new Printer();
		for(int i=0;i<emptyThread.length;i++){
			emptyThread[i]=false;
		}
		while(true){
			boolean isOK=true;
			for(int i=0;i<emptyThread.length;i++){
				if(emptyThread[i]==false){
					isOK=false;
					break;
				}
			}
			if(isOK==true){
				//所有线程执行完毕
				try{
					Worm.writeFooter();
					int idx=Printer.sb.lastIndexOf(",");
					String str1=Printer.sb.substring(0, idx);
					String str2=Printer.sb.substring(idx+1, Printer.sb.length());
					String result=str1+str2;
					fw=new FileWriter("./tiebaworm-"+Printer.filename);
					fw.write(result);
					fw.flush();
					fw.close();
				}catch(Exception e){
					e.printStackTrace();
				}
				//p.println("All the worms have been over");
				MyFrame.addOutput("[DEBUG]All the worms have been over.");
				break;
			}
			try{
				Thread.sleep(1000);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}
