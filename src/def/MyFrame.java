package def;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
public class MyFrame extends JFrame implements ActionListener{
	JButton startBtn;
	JTextArea textarea;
	JPanel top,bottom,p1,p2;
	JTextField url_t,pageBegin_t,pageEnd_t;
	JLabel output_l,url_l,pageBegin_l,pageEnd_l;
	public static void main(String[] args) {
		MyFrame myFrame=new MyFrame();
		myFrame.frame();
	}
	public void frame(){
		startBtn=new JButton("Start");
		textarea=new JTextArea();
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		bottom=new JPanel();
		bottom.setLayout(new GridLayout(2,1));
		p1=new JPanel();
		p1.setLayout(new FlowLayout());
		p2=new JPanel();
		p2.setLayout(new FlowLayout());
		bottom.add(p1);
		bottom.add(p2);
		url_t=new JTextField(30);
		pageBegin_t=new JTextField(5);
		pageEnd_t=new JTextField(5);
		output_l=new JLabel("Output:                                       Code by juzeon.");
		url_l=new JLabel("Url:");
		pageBegin_l=new JLabel("Pages begin with:(number)");
		pageEnd_l=new JLabel("Pages end with:(number)");
		startBtn.setActionCommand("startBtn");
		startBtn.addActionListener(this);
		p1.add(url_l);
		p1.add(url_t);
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
		this.setSize(800,550);
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
			String url=url_t.getText();
			if(JOptionPane.showConfirmDialog(null,"Your input:\nurl:"+url+"\npageBegin:"+pageBegin+"\npageEnd:"+pageEnd+
					"\nAre you ready to start the worms now?","Check your input:",JOptionPane.YES_NO_OPTION)==1){
				return;
			}
			startWorm(pageBegin, pageEnd);
		}
	}
	public void startWorm(int pageBegin,int pageEnd){
		System.out.println("start!");
	}
	public boolean checkField(){
		if(isNullOrEmpty(url_t.getText())||isNullOrEmpty(pageBegin_t.getText())||isNullOrEmpty(pageEnd_t.getText())){
			JOptionPane.showMessageDialog(null,"Url,pageBegin or pageEnd is empty!","Check your input:",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if(!((url_t.getText().startsWith("http://")||(url_t.getText().startsWith("https://"))))){
			JOptionPane.showMessageDialog(null,"Url must start with 'http://' or 'https://'.","Check your input:",JOptionPane.ERROR_MESSAGE);
			return false;
		}
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
