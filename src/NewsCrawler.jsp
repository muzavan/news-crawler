//ini sebagian aja , belum digabungin sama tampilan


<% @page import="java.util.io,NewsCrawler.NewsCrawler" %>

<%
	
	//ngambil dari lemparan method POST atau GET dari halaman lain
	String rootURL = (String) request.getParameter("rootURL"); //alamat yg pengen dicrawl (crawl pake BFS)
	int maxURL = Integer.valueOf(request.getParameter("maxURL")); //jumlah maksimum
	NewsCrawler crawler = new NewsCrawler(rootURL,maxURL);
	crawler.crawling();
	if(crawler.getKontenChild().size()!=maxURL){
		//kalo gagal crawl, entah karena websitenya salah atau timeout
	}
	else{
		//kalo websitenya udah bener
		//yaudah tinggal belajar
	}


%>
