package WebCrawler;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
	/**
     * Internal method to Extract and print all the links embedded in the web page in an order
     * @param url - extracts all the links in the given URL. 
    */
	private static LinkedHashSet<URL> extract(URL url) 
	{
		LinkedHashSet<URL> all_links = new LinkedHashSet<URL>();//hashset where all the links of the url will be saved.
		Pattern pat = Pattern.compile("href=\"((http://|https://|www).*?)\"", 
				Pattern.DOTALL);//checks for few common patterns of the URL.
		Matcher mat = pat.matcher(fetch(url));//stores the link with the matching pattern under the matcher variable mat.
        //Extracting the data from all the matching Urls and checking for invalid urls.
		while (mat.find()) 
		{
			String linkStr = normalize(mat.group(1));
			try {
				URL link = new URL(linkStr);
				all_links.add(link);
			}
			catch (MalformedURLException e) {
				System.err.println("Page at " + url + " has a link to "
						+ "invalid URL : " + linkStr + ".");
			}
		}
		return all_links;
	}

	/**
     * Internal method to fetch and return the contents from the URL
     * @param url - extracts all the links in the given URL. 
    */
	private static String fetch(URL url) {
		StringBuilder stringBuilder = new StringBuilder();//creating a stringBuilder
		try {
			//input stream to open all the crawled URLs
			BufferedReader input = new BufferedReader(new InputStreamReader(url.openStream()));
			String input_Line;
			//reads the lines on all the input URLs and stores it in the string input_Line
			while ((input_Line = input.readLine()) != null)
				stringBuilder.append(input_Line);//appends the input_Line if the till the input is null
			input.close();
		}
		catch (IOException e) {
			System.err.println("Page not found at " + url);
		}
		return stringBuilder.toString();
	}
	/**
     * Internal method to Transform from a URL to URL object by the way of normalizing.
     * @param urlStr - URL input to convert to an URL object by normalization.
    */
	private static String normalize(String urlStr) 
	{
		if (!urlStr.startsWith("http"))
			urlStr = "http://" + urlStr;//appends the string "http://" to the url
		if (urlStr.endsWith("/")) 
			urlStr = urlStr.substring(0, urlStr.length() - 1);//adds the substring and reduces length by 1 to the url if "/" is found
		if (urlStr.contains("#")) 
			urlStr = urlStr.substring(0, urlStr.indexOf("#"));//adds the substring and indexes the url if "#" is found
		return urlStr;
	}
	/**
     * Internal method to store the URLs crawled using a list.
     * @param start - URL of the starting webpage to crawl. 
     * @param limit - Maximum size of the URLs that can be crawled.
    */
	public static List<URL> crawl(URL start, int limit) {
		List<URL> urlsList = new ArrayList<URL>(limit);
		urlsList.add(start); //adding the starting URL into the URLlist to crawl
        Set<URL> urlsTemp = new HashSet<URL>(urlsList);//creating a copy of URLs under the urlsTemp Hashset to improve the execution speed
		int i = 0;
		while (urlsList.size() < limit && i < urlsList.size()) //While Conditional loop to check if the urlList size within the limit specified
		{
			URL presenttUrl = urlsList.get(i);//stores the iteration of URLs based on the i value to the presenttUrl value
			for (URL url : extract(presenttUrl)) //calls the extract method with the presentURL value
			{
				if (urlsTemp.add(url)) //if condition to add the extracted URL to the urlsTemp hashset
				{
					urlsList.add(url);//adds the extracted URL to the urlsTemp hashset
					if (urlsList.size() == limit)//checking if the size of the urlList is within the given limit
						break;
				}
           }
           i++;
       }
       return urlsList; //returns the complete URL lists which are used for crawling
	}

	/**
     * Start of the main function for the Webcrawler feature
	 * @throws IOException 
    */
	public static void main(String[] args) throws IOException {
		try {
			URL people = new URL("https://people.com/");//source URL which is used for crawling
			int limit = 200;//maximum size/limit of the URLs to be crawled and indexed
            List<URL> discovered = WebCrawler.crawl(people, limit);//calling the method crawl with the start URL and limit
			System.out.println("/n Results: /n ");
			int i = 1;
			Iterator<URL> iterator = discovered.iterator();//object to iterate the URLs
			//FileWriter fileoutput = new FileWriter("people_urls.txt");
			while (iterator.hasNext() && i <= 200) //iterated the URLs from 1 to 1000(limit)
			{
				System.out.println(iterator.next());
//				String s=iterator.next().toString();
//				System.out.println(s);
//				if(!(s.contains("|")||s.contains(".woff2")||s.contains(".png")||s.contains(".ico")||s.contains(".js")||s.contains("pinterest")||s.contains("allpeoplequilt"))){
//					fileoutput.write(s+"\n");
//				}
//					
				
				i++;
				
			}
			//fileoutput.close();
			
				
			
			
		}
		catch (MalformedURLException e) {
			System.err.println("The URL to start crawling with is invalid.");
		}
	}
    
	
}