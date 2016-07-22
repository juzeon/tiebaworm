package def;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
/*
 * 即将加入：
 * 1.文件名自定义
 * 2.更好的设置界面
 * 3.停止按钮
 * */
public class MyFrame extends JFrame implements ActionListener{
	JButton startBtn,showExample;
	static JTextArea textarea;
	JPanel top,bottom,p1,p2;
	JTextField tiebaName_t,pageBegin_t,pageEnd_t,filename_t;
	JLabel output_l,tiebaName_l,pageBegin_l,pageEnd_l,filename_l;
	static StringBuilder output=new StringBuilder();
	public static void main(String[] args) {
		MyFrame myFrame=new MyFrame();
		myFrame.frame();
	}
	public static void addOutput(String outputText){
		output.insert(0,outputText+"\n");
		textarea.setText(output.toString());
		//textarea.paintImmediately(textarea.getBounds());
	}
	public void frame(){
		startBtn=new JButton("Start");
		showExample=new JButton("Show example");
		textarea=new JTextArea();
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		try{
			InputStream is=this.getClass().getResourceAsStream("/res/draw.txt");   
			BufferedReader br=new BufferedReader(new InputStreamReader(is));  
			String s="";
			while((s=br.readLine())!=null){
				output.append(s+"\n");
			}
			textarea.setText(output.toString());
		}catch(Exception e){
			//e.printStackTrace();
			addOutput("[WARNING]Can't read draw.txt:"+e.getMessage());
		}
		bottom=new JPanel();
		bottom.setLayout(new GridLayout(2,1));
		p1=new JPanel();
		p1.setLayout(new FlowLayout());
		p2=new JPanel();
		p2.setLayout(new FlowLayout());
		bottom.add(p1);
		bottom.add(p2);
		tiebaName_t=new JTextField(30);
		filename_t=new JTextField(10);
		filename_t.setText(Printer.filename);
		pageBegin_t=new JTextField(5);
		pageEnd_t=new JTextField(5);
		output_l=new JLabel("Output:                                       Code by juzeon.");
		tiebaName_l=new JLabel("Tieba name:");
		pageBegin_l=new JLabel("Pages begin with:(number)");
		pageEnd_l=new JLabel("Pages end with:(number)");
		filename_l=new JLabel("Output file name:    tiebaworm-");
		startBtn.setActionCommand("startBtn");
		startBtn.addActionListener(this);
		showExample.setActionCommand("showExample");
		showExample.addActionListener(this);
		p1.add(tiebaName_l);
		p1.add(tiebaName_t);
		p1.add(filename_l);
		p1.add(filename_t);
		p1.add(showExample);
		p2.add(pageBegin_l);
		p2.add(pageBegin_t);
		p2.add(pageEnd_l);
		p2.add(pageEnd_t);
		p2.add(startBtn);
		this.setLayout(new BorderLayout());
		this.add(output_l,BorderLayout.NORTH);
		this.add(new JScrollPane(textarea),BorderLayout.CENTER);
		this.add(bottom,BorderLayout.SOUTH);
		this.setTitle("Tieba worm");
		this.setSize(980,550);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("startBtn")){
			if(checkField()==false){
				return;
			}
			int pageBegin=0;
			int pageEnd=0;
			try{
				pageBegin=Integer.parseInt(pageBegin_t.getText());
				pageEnd=Integer.parseInt(pageEnd_t.getText());
			}catch(Exception er){
				JOptionPane.showMessageDialog(null,er.getMessage(),"There is something wrong with pageBegin/pageEnd:",JOptionPane.ERROR_MESSAGE);
				return;
			}
			String filename=filename_t.getText();
			String tiebaName_encode="";
			String tiebaName_decode=tiebaName_t.getText();
			try {
				tiebaName_encode=URLEncoder.encode(tiebaName_t.getText(),"UTF-8");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(JOptionPane.showConfirmDialog(null,"Your input:\ntieba name:"+tiebaName_decode+"\npageBegin:"+pageBegin+"\npageEnd:"+pageEnd+
					"\nAre you ready for it?","Check your input:",JOptionPane.YES_NO_OPTION)==1){
				return;
			}
			startWorm(tiebaName_encode,filename,pageBegin, pageEnd);
		}else if(e.getActionCommand().equals("showExample")){
			JOptionPane.showMessageDialog(null,"Tieba name:minecraft\nOutput file name:minecraft-bar.json\nPages begin with:1\nPages end with:10","An example:",JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public void startWorm(String tiebaName,String filename,int pageBegin,int pageEnd){
		Printer.filename=filename;
		Thread et=new Thread(new EmptyThread(pageEnd-pageBegin+1));//27
		et.start();
		Worm.writeHeader();
		for(int i=pageBegin-1;i<pageEnd;i++){
			new Thread(new Worm(tiebaName,i,i+1)).start();
		}
	}
	public boolean checkField(){
		if(isNullOrEmpty(filename_t.getText())||isNullOrEmpty(tiebaName_t.getText())||isNullOrEmpty(pageBegin_t.getText())||isNullOrEmpty(pageEnd_t.getText())){
			JOptionPane.showMessageDialog(null,"Url,filename,pageBegin or pageEnd is empty!","Check your input:",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		/*if(!((url_t.getText().startsWith("http://")||(url_t.getText().startsWith("https://"))))){
			JOptionPane.showMessageDialog(null,"Url must start with 'http://' or 'https://'.","Check your input:",JOptionPane.ERROR_MESSAGE);
			return false;
		}*/
		return true;
	}

    public boolean isNullOrEmpty(Object obj) {  
        if (obj == null)  
            return true;  
  
        if (obj instanceof CharSequence)  
            return ((CharSequence) obj).length() == 0;  
  
        if (obj instanceof Collection)  
            return ((Collection) obj).isEmpty();  
  
        if (obj instanceof Map)  
            return ((Map) obj).isEmpty();  
  
        if (obj instanceof Object[]) {  
            Object[] object = (Object[]) obj;  
            if (object.length == 0) {  
                return true;  
            }  
            boolean empty = true;  
            for (int i = 0; i < object.length; i++) {  
                if (!isNullOrEmpty(object[i])) {  
                    empty = false;  
                    break;  
                }  
            }  
            return empty;  
        }  
        return false;  
    }  
}
