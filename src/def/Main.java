package def;
import java.io.*;
import java.util.*;
import java.net.*;
//import net.sf.json.*;
/*
 * 
 * */
public class Main implements Runnable{
	Printer p;
	int pageBegin,pageEnd;
	ArrayList<String> topicsString;
	ArrayList<Topic> topics;
	public static FileWriter fw;
	public static void main(String[] args) throws Exception{
		Thread et=new Thread(new EmptyThread(27));//27
		et.start();
		writeHeader();
		for(int i=0;i<27;i++){
			new Thread(new Main(i,i+1)).start();
		}
		/*Main no1=new Main(0,2);
		Thread no1_thread=new Thread(no1);
		no1_thread.start();*/
		/*new Thread(new Main(0,1)).start();
		new Thread(new Main(1,2)).start();*/
	}
	public static void writeHeader(){
		try {
			fw=new FileWriter("./tiebaworm-"+Printer.filename+".txt",true);
			Printer.sb.append("{\n\"data\":[\n");
			fw.write("{\n\"data\":[\n");
			fw.flush();
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void writeFooter(){
		try {
			fw=new FileWriter("./tiebaworm-"+Printer.filename+".txt",true);
			Printer.sb.append("\n]\n}");
			fw.write("\n]\n}");
			fw.flush();
			fw.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run(){
		// TODO Auto-generated method stub
		topicsString=new ArrayList<String>();
		topics=new ArrayList<Topic>();
		p=new Printer();
		for(int i=pageBegin;i<pageEnd;i++){
			getTopic(i);
		}
		workTopic();
		printToFile();
		EmptyThread.emptyThread[pageBegin]=true;
		p.println("Worm No."+pageBegin+" has been over");
	}   
	public synchronized void printToFile(){
		try{
			fw=new FileWriter("./tiebaworm-"+Printer.filename+".txt",true);
			for(int i=0;i<topics.size();i++){
				String title=topics.get(i).title;
				String id=topics.get(i).id;
				String text=topics.get(i).text;
				String user=topics.get(i).user;
				title=title.replaceAll("\"","'");
				title=title.replaceAll("\\\\","/");
				text=text.replaceAll("\"","'");
				text=text.replaceAll("\\\\","/");
				user=user.replaceAll("\"","'");
				/*if(i==topics.size()-1){
					fw.write("{\"id\":\""+id+"\",\"title\":\""+title+"\",\"text\":\""+text+"\",\"user\":\""+user+"\"}\n");
				}else{
					fw.write("{\"id\":\""+id+"\",\"title\":\""+title+"\",\"text\":\""+text+"\",\"user\":\""+user+"\"},\n");
				}*/
				Printer.sb.append("{\"id\":\""+id+"\",\"title\":\""+title+"\",\"text\":\""+text+"\",\"user\":\""+user+"\"},\n");
				fw.write("{\"id\":\""+id+"\",\"title\":\""+title+"\",\"text\":\""+text+"\",\"user\":\""+user+"\"},\n");
			}
			/*fw.write("\n]\n}");*/
			fw.flush();
			fw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public Main(int pageBegin,int pageEnd){
		this.pageBegin=pageBegin;
		this.pageEnd=pageEnd;
	}
	public void getTopic(int page){
		String url="http://tieba.baidu.com/f?kw=%E5%B0%91%E5%B9%B4%E7%94%B5%E8%84%91%E4%B8%96%E7%95%8C&ie=utf-8&pn="
		+workPage(page);
		String html=sendGet(url);
		String[] footer=html.split("<span class=\"card_numLabel\">贴子：</span>");
		String[] header=footer[1].split("共有主题数<span class=\"red_text\">");
		//String[] topicStr=header[0].split("<div class=\"threadlist_abs threadlist_abs_onlyline");
		String[] topicStr=header[0].split("<div class=\"col2_right j_threadlist_li_right \">");
		
		for(int i=0;i<topicStr.length-1;i++){
			topicsString.add(topicStr[i+1]);
		}
		
		/*topicsString.add(topicStr[1]);
		topicsString.add(topicStr[2]);*/
	}
	public void workTopic(){
		for(int i=0;i<topicsString.size();i++){
			String atopic=topicsString.get(i);
			String id=getMiddleText(atopic, "<a href=\"/p/", "\" title=");
			String title=getMiddleText(atopic,"target=\"_blank\" class=\"j_th_tit \">","</a></div><div class=\"threadlist_author pull_right");
			//String user=getMiddleText(atopic,"title=\"主题作者: ","\"data-field=(.*?)><i class=\"icon_author\"></i><span class");
			String user=getMiddleText(atopic,"title=\"主题作者: ","\"(.*?)data-field");
			String text=getArticle(id);
			p.println("Article title："+title);
			topics.add(new Topic(id,title,text,user));
		}
	}
	public String getArticle(String id){
		String url="http://tieba.baidu.com/p/"+id;
		String html=sendGet(url);
		return getMiddleText(html,"<div id=\"post_content_(\\d*?)\" class=\"d_post_content j_d_post_content \">", "</div>");
	}
	public String getMiddleText(String text,String header,String footer){
		String[] work_footer=text.split(header);
		String[] work_header=work_footer[1].split(footer);
		return work_header[0];
	}
	public int workPage(int page){
		if(page==0){
			return 0;
		}else{
			return page*50;
		}
	}
    public String sendGet(String url) {    
        String result = "";    
        BufferedReader in = null;      
        try {    
            String urlNameString = url;    
            URL realUrl = new URL(urlNameString);    
            // 打开和URL之间的连接    
            URLConnection connection = realUrl.openConnection();    
            // 设置通用的请求属性    
            connection.setRequestProperty("accept", "*/*");    
            connection.setRequestProperty("connection", "Keep-Alive");    
            connection.setRequestProperty("user-agent",    
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 建立实际的连接    
            connection.connect();    
            // 获取所有响应头字段    
            Map<String, List<String>> map = connection.getHeaderFields();    
            // 遍历所有的响应头字段    
            /*for (String key : map.keySet()) {    
                System.out.println(key + "--->" + map.get(key));    
            } */   
            // 定义 BufferedReader输入流来读取URL的响应    
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));    
            String line;    
            while ((line = in.readLine()) != null) {    
                result += line;    
            }    
        } catch (Exception e) {    
            System.err.println("Sent get error: " + e);   
        }    
        // 使用finally块来关闭输入流    
        finally {    
            try {    
            	System.out.println("Sent get message '"+url+"' successfully!");
                if (in != null) {    
                    in.close();    
                }    
            } catch (Exception e2) {    
                e2.printStackTrace();    
            }    
        }
        return result;    
    } 
}
