package def;
import java.io.*;
import java.util.*;
import java.net.*;
/*
 * 
 * */
public class Main implements Runnable{
	Printer p;
	ArrayList<String> topicsString;
	ArrayList<Topic> topics;
	public static void main(String[] args) throws Exception{
		Main lxl=new Main();
		lxl.start();
	}
	public void start() throws Exception{
		topicsString=new ArrayList<String>();
		topics=new ArrayList<Topic>();
		p=new Printer();
		getTopic(0);
		workTopic();
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
	}
	public void workTopic(){
		for(int i=0;i<topicsString.size();i++){
			String atopic=topicsString.get(i);
			String id=getMiddleText(atopic, "<a href=\"/p/", "\" title=");
			String title=getMiddleText(atopic,"target=\"_blank\" class=\"j_th_tit \">","</a></div><div class=\"threadlist_author pull_right");
			String user=getMiddleText(atopic,"title=\"主题作者: ","\"data-field=(.*)><i class=\"icon_author\"></i><span class");
			String text=getArticle(id);
			topics.add(new Topic(id,title,text,user));
		}
	}
	public String getArticle(String id){
		String url="http://tieba.baidu.com/p/"+id;
		String html=sendGet(url);
		return getMiddleText(html,"class=\"d_post_content j_d_post_content \">", "<div class=\"user-hide-post-down\" style=\"display: none;\">");
		
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
            // 建立实际的连接    
            connection.connect();    
            // 获取所有响应头字段    
            Map<String, List<String>> map = connection.getHeaderFields();    
            // 遍历所有的响应头字段    
            for (String key : map.keySet()) {    
                System.out.println(key + "--->" + map.get(key));    
            }    
            // 定义 BufferedReader输入流来读取URL的响应    
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));    
            String line;    
            while ((line = in.readLine()) != null) {    
                result += line;    
            }    
        } catch (Exception e) {    
            System.err.println("发送GET请求出现异常！" + e);    
            e.printStackTrace();    
        }    
        // 使用finally块来关闭输入流    
        finally {    
            try {    
                if (in != null) {    
                    in.close();    
                }    
            } catch (Exception e2) {    
                e2.printStackTrace();    
            }    
        }    
    
        return result;    
    }
	public void run() {
		// TODO Auto-generated method stub
		
	}    
}
