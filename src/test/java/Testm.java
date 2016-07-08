import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.util.HtmlUtils;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class Testm {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        String part ="&lt;p&gt;asdf&lt;img src=&quot;https://221.224.53.27/weixinPic/upload1/20160705/64621467689589302.png&quot; title=&quot;3.png&quot; style=&quot;width: 100px; height: 100px;&quot; width=&quot;100&quot; height=&quot;100&quot; border=&quot;0&quot; hspace=&quot;0&quot; vspace=&quot;0&quot;/&gt;&lt;img src=&quot;https://221.224.53.27/weixinPic/upload1/20160705/64621467689589302.png&quot; title=&quot;3.png&quot; style=&quot;width: 100px; height: 100px;&quot; width=&quot;100&quot; height=&quot;100&quot; border=&quot;0&quot; hspace=&quot;0&quot; vspace=&quot;0&quot;/&gt;&lt;/p&gt;";
		part = HtmlUtils.htmlUnescape(part);
		  Document doc = Jsoup.parseBodyFragment(part);
	        Element body = doc.body();
	        Elements elements = body.getElementsByTag("img");
	        for (Element element : elements) {
	        	 System.out.println(element.attr("src"));
	        	 element.attr("src", "new");
			}
	        System.out.println(body.html());
	        

	}

}
