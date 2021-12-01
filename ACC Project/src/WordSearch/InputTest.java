package WordSearch;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class InputTest {	
	
	/**
	 * Internal method to fetch and return the contents from the URL
	 * @param url - extracts all the links in the given URL. 
	*/
	public static void pagerank(Map<String, Integer> unsortedURLsLinks) 
	{
		
	LinkedHashMap<String, Integer> prioritySortedMap = 
			new LinkedHashMap<>();//creating a LinkedHashMap to store the URLs based on priorty/ranking basis
	unsortedURLsLinks.entrySet().stream().sorted
	(Map.Entry.comparingByValue(Comparator.reverseOrder()))
	        .forEachOrdered(x -> prioritySortedMap.put
	        		(x.getKey(), x.getValue()));//sorting the unsortedURL links by comparing the map entry with the comparator in reverse order
	System.out.println("Ranking: \t  URL:");
	for (Map.Entry<String, Integer> entry : prioritySortedMap.entrySet())
	    System.out.println(entry.getValue()+"\t \t"+entry.getKey());//prints the Page based on the priority ranking
	}
	
	
	/**
	 * Start of the main class for the search operation.
	*/
	public static void main(String[] args) throws IOException {
		
		//retrieving urls from websites.txt for user input word to pass through search engine.
		 
		System.out.println("Trie generation for people.com");

		SearchEngine searchEngine = new SearchEngine("people_urls.txt");
		System.out.println();
		System.out.println("Keyword to search in people.com");

		//Enter a word you want to search
		String input = new Scanner(System.in).next();

		try {
			while (!input.equals("exit") && !input.equals(null)) {
				//Indexing each URL in the webpage to rank them based on the priority
				String[] indexOfArray = input.split("[[,]*|[ ]*]+");
				String[] webpagesList = searchEngine.search(indexOfArray);
				try {
					if (webpagesList == null) 
						System.out.println("Enter a keyword:");
					Map<String, Integer> unsortedURLsLinks = null;
					//storing the unsorted URLs in a hash table
					unsortedURLsLinks = new HashMap<>();
					
					//insertion of links to the unsortedURLsLinks based on the count of input word
					for (String url : webpagesList) {
						unsortedURLsLinks.put(url, WordCount.getWordCount(url, input));
					}
					
					pagerank(unsortedURLsLinks);// Calling the page Ranking function
					

					}
					
					catch (NullPointerException e) {
						System.out.println("sorry");
						}

				System.out.println("\nSearch again (search multiple words separated "
						+ "by comma");
				System.out.println(" or enter\"exit\" to end:");
				input = new Scanner(System.in).next();
			}
		} catch (NullPointerException e) {
			System.out.println("No value is passed");
		}
	}
}