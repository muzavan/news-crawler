import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class NewsCrawler {
	private String rootURL; //alamat awal URL yang akan dicrawl
	private int maxURL; // jumlah maksimal URL yang bisa diretrieve
	private ArrayList<String> childURL; //kumpulan dari URL yang berhasil diretrieve
	private ArrayList<String> kontenChild;
	private ArrayList<String> titleChild;
	private final int TIME_OUT = 10000;
	public NewsCrawler(){
		childURL = new ArrayList<String>();
		kontenChild = new ArrayList<String>();
		titleChild = new ArrayList<String>();
		rootURL = null;
		maxURL = 0;
	}
	
	public NewsCrawler(String rootURL, int maxURL){
		childURL = new ArrayList<String>();
		kontenChild = new ArrayList<String>();
		titleChild = new ArrayList<String>();
		this.rootURL = rootURL;
		this.maxURL = maxURL;
	}
	
	public void crawling(){
		if(rootURL==null || maxURL==0){
			//belum di configurasi untuk melakukan crawl
			System.out.println("Cant Crawl. Please set rootURL and maxURL before.");
		}
		else{
			childURL.add(rootURL);
				try{
					kontenChild.add(Jsoup.parse(new URL(rootURL), TIME_OUT).body().text());
					titleChild.add(Jsoup.parse(new URL(rootURL), TIME_OUT).title());
					int i=0;
					Document doc;
					Elements links;
					// "memenuhi childURL dengan kuota maxURL"
					while(childURL.size()<maxURL){
						doc = Jsoup.parse(new URL(childURL.get(i)), TIME_OUT);
						links = doc.getElementsByTag("a"); //mengambil semua node dengan tag a
						for(Element link : links){
							//mengambil masing-masing link hingga berjumlah sebanyak maxURL
							if(childURL.size()<maxURL){
								String tmp = link.attributes().get("href");
								//System.out.println(tmp);
								if(tmp.toLowerCase().startsWith("http://") || tmp.toLowerCase().startsWith("https://") ){
									// do nothing
								}
								else{
									tmp = childURL.get(childURL.size()-1)+"/"+tmp;
								}
								System.out.println(tmp);
								childURL.add(tmp);
								System.out.println(childURL.get(childURL.size()-1));
								try{
									kontenChild.add(Jsoup.parse(new URL(childURL.get(childURL.size()-1)), TIME_OUT).body().text());
									titleChild.add(Jsoup.parse(new URL(childURL.get(childURL.size()-1)), TIME_OUT).title());
									System.out.println("Berhasill");
								}
								catch(Exception e){
									System.out.println(tmp + "Skipped");
									childURL.remove(childURL.get(childURL.size()-1));
								}
							}
							else{
								break;
							}
						}
						i++;
					}
					System.out.println("Selesai");
				}catch(Exception e){
					System.out.println(e+"\n Maaf Crawling Gagal");
				}
		}
	}
	
	// Setter Getter
	public String getRootURL() {
		return rootURL;
	}
	public void setRootURL(String rootURL) {
		this.rootURL = rootURL;
	}
	public int getMaxURL() {
		return maxURL;
	}
	public void setMaxURL(int maxURL) {
		this.maxURL = maxURL;
	}
	public ArrayList<String> getChildURL() {
		return childURL;
	}
	public void setChildURL(ArrayList<String> childURL) {
		this.childURL = childURL;
	}
	public ArrayList<String> getKontenChild() {
		return kontenChild;
	}
	public void setKontenChild(ArrayList<String> kontenChild) {
		this.kontenChild = kontenChild;
	}
	
	public static void main(String[] args){
		NewsCrawler crawl = new NewsCrawler("http://news.detik.com/read/2014/11/30/155040/2763292/10/detik-detik-saat-nadjib-yang-jadi-korban-tabrak-lari-di-cipularang-tewas?9911012",10);
		crawl.crawling();
		for(String s : crawl.getKontenChild()){
			System.out.println(s);
		}
    }
}
